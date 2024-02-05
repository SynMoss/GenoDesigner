# -*- coding: utf-8 -*-
"""
Created on Thu Jan  4 16:38:06 2024

@author: Sam_Y
"""

# -*- coding: utf-8 -*-
"""
Created on Wed Dec 27 08:51:19 2023

@author: Sam_Y
Function: Global insertion
"""

import pandas as pd
from Bio import SeqIO
from Bio.Seq import Seq
from Bio.SeqRecord import SeqRecord
from Bio.SeqFeature import SeqFeature, ExactPosition, SimpleLocation, CompoundLocation
from GenoDesigner import merge_intervals, feature_split, seq_insert, feature_not_split,getName
import multiprocessing
from flask import jsonify
from GenbankUtil import newFeature
import os
def globalInsertion(param,inputPath,outPath,operateIdLong):
    operateId = str(operateIdLong)
    Ins_ERR = []
    insert_ERR_report_df_list = []
    insert_report_df_list = []
    insert_report_ERR_list = []
    insert_number_list = []
    insert_report_list = []
    
    core = 4
    
    ins_seq = param['sequence']

    ins_name = param['featureName']
    ins_type = param['featureType']

    # ins_fea_info = {'CDS':['-', 3, False, 1, 1]}
    ins_fea_info = param['featureMap']
    print(ins_fea_info)
    split_feature = False
    # ins_no_type = ['CDS']
    ins_no_type = param['typeList']


    #ins_seq = 'CATCACCATCACCATCAC'
    #ins_name = '6xHis Tag'
    #ins_type = 'CDS'
    #ins_fea_info = {'CDS':['-', -3, True, 1, 1]}
    #split_feature = False
    #ins_no_type = []



    ins_rec = SeqRecord(Seq(ins_seq))
    ins_rec.annotations['molecule_type'] = 'DNA'
    ins_rec.annotations['topology'] = 'linear'
    sf = SeqFeature(SimpleLocation(0, len(ins_seq), strand = 1), type = ins_type)
    sf.qualifiers['locus_tag'] = [ins_name]
    sf.qualifiers['label'] = [ins_name]
    ins_rec.features.append(sf)

    record = SeqIO.read(inputPath, 'genbank')
    insert_list = []
    ins_no_range = []

    for fea in record.features:
        if fea.type in ins_fea_info:
            if not ins_fea_info[fea.type][0] == '-':
                if ins_fea_info[fea.type][2]:
                    st = ins_fea_info[fea.type][3] * fea.location.strand
                else:
                    st = 1
                insert_list.append([fea,
                                    int((1+fea.location.strand)/2*fea.location.start + (1-fea.location.strand)/2*fea.location.end - fea.location.strand * ins_fea_info[fea.type][0]),
                                    st
                                    ])
            if not ins_fea_info[fea.type][1] == '-':
                if ins_fea_info[fea.type][2]:
                    st = ins_fea_info[fea.type][4] * fea.location.strand
                else:
                    st = 1
                insert_list.append([fea,
                                    int((1-fea.location.strand)/2*fea.location.start + (1+fea.location.strand)/2*fea.location.end + fea.location.strand * ins_fea_info[fea.type][1]),
                                    st
                                    ])
        if fea.type in ins_no_type:
            ins_no_range.append([int(fea.location.start), int(fea.location.end)])
    insert_list.sort(key = lambda x:x[1])
    ins_no_range = merge_intervals(ins_no_range)
    ins_no_range = merge_intervals(ins_no_range)
    ins_no_range = merge_intervals(ins_no_range)

    insert_fn_list = []
    insert_fn_list_unique = []
    insert_ERR_list = []
    insert_pos_list = []
    for pos in insert_list:
        cp = 0
        for iv in ins_no_range:
            if pos[1] > iv[0] and pos[1] < iv[1]:
                cp = 1
        if pos[1] < 0 or pos[1] > len(record.seq):
            cp = 1
        if cp == 0:
            if not pos[1] in insert_pos_list:
                insert_fn_list_unique.append([pos[0], pos[1], (len(insert_fn_list_unique) + 1) * len(ins_seq), pos[2]])
                insert_pos_list.append(pos[1])
            insert_fn_list.append([pos[0], pos[1], (len(insert_fn_list_unique) + 1) * len(ins_seq), pos[2]])
        else:
            insert_ERR_list.append(pos)
    if len(insert_fn_list_unique)==0:
        return jsonify({
        "code": 500,
        "msg": 'Script failed:insert_fn_list_unique is empty'})
    insert_iv_list = [[0, insert_fn_list_unique[0][1], 0, insert_fn_list_unique[0][3]]]
    for i in range(len(insert_fn_list_unique) - 1):
        insert_iv_list.append([insert_fn_list_unique[i][1], insert_fn_list_unique[i+1][1], insert_fn_list_unique[i][2], insert_fn_list_unique[i + 1][3]])
    insert_iv_list.append([insert_fn_list_unique[-1][1], len(record.seq), insert_fn_list_unique[-1][2], '-'])
    sf_add = []
    pool = multiprocessing.Pool(core)
    ag = []
    for fea in record.features:
        ag.append((fea, insert_iv_list))
    if split_feature:
        result = pool.map(feature_split, ag)
    else:
        result = pool.map(feature_not_split, ag)
    for sf in result:
        if sf:
            sf_add.append(sf)
    result = None
    ag = None

    record_ins = SeqRecord(Seq(''))
    record_ins.annotations['molecule_type'] = 'DNA'
    record_ins.annotations['topology'] = 'linear'
    ag = []
    for i in range(core):
        ag.append([record, insert_iv_list[int(i*len(insert_iv_list)/core):int((i + 1)*len(insert_iv_list)/core)], ins_rec])
    ag[-1][1] += insert_iv_list[int((i + 1)*len(insert_iv_list)/core):]
    pool = multiprocessing.Pool(core)
    result = pool.map(seq_insert, ag)
    result.sort(key = lambda x: x[1])
    ag = None
    for part in result:
        record_ins += part[0]
    result = None
    record_ins.features+=sf_add
    record_ins.features.sort(key=lambda x: x.location.start)
    record_ins.annotations['molecule_type'] = 'DNA'
    record_ins.annotations['topology'] = 'linear'
    features = []
    for feature in record_ins.features:
        features.append(newFeature(feature))
    with open(outPath, 'w') as f:
        f.write(str(record_ins.seq))
    insert_number_list.append([record.id, len(insert_fn_list_unique)])
    insert_report_list = []
    insert_report_ERR_list = []
    for item in insert_fn_list:
        name = getName(item[0].qualifiers)
        insert_report_list.append([name, item[0].type, item[0].location.start, item[0].location.end, item[3], item[1]])
    insert_report_df = pd.DataFrame(insert_report_list, columns = ['Feature_name', 'Feature_type', 'Feature_start', 'Feature_end', 'Feature_strand', 'Insert_position'])
    if insert_report_list:
        insert_report_df.loc[:, 'Chr'] = record.id
    else:
        insert_report_df.insert(0,"Chr",[])
    insert_report_df.loc[:, 'Chr'] = record.id
    insert_report_df = insert_report_df[['Chr', 'Feature_name', 'Feature_type', 'Feature_start', 'Feature_end', 'Feature_strand', 'Insert_position']]
    insert_report_df_list.append(insert_report_df)
    for item in insert_ERR_list:
        name = getName(item[0].qualifiers)
        insert_report_ERR_list.append([name, item[0].type, item[0].location.start, item[0].location.end, item[2], item[1]])
    insert_ERR_report_df = pd.DataFrame(insert_report_ERR_list, columns = ['Feature_name', 'Feature_type', 'Feature_start', 'Feature_end', 'Feature_strand', 'Insert_ERR_position'])
    if insert_report_ERR_list:
        insert_ERR_report_df.loc[:, 'Chr'] = record.id
    else:
        insert_ERR_report_df.insert(0,"Chr",[])
    insert_ERR_report_df = insert_ERR_report_df[['Chr', 'Feature_name', 'Feature_type', 'Feature_start', 'Feature_end', 'Feature_strand', 'Insert_ERR_position']]
    folderName = os.path.dirname(outPath)
    with pd.ExcelWriter(folderName+"/"+operateId+'.xlsx',engine='xlsxwriter') as writer:
        insert_report_df.to_excel(writer,sheet_name="insert_report_df",index=False)
        insert_ERR_report_df.to_excel(writer,sheet_name="insert_ERR_report_df",index=False)
    with open(os.path.dirname(outPath)+"/"+operateId+'.txt', 'w') as hd:
        hd.write('Function: Insert by Feature Type\nArgument:\nFeature to insert:')
        #for item in preserve_list:
        #    hd.write(' ' + item + '(' + str(preserve_list[item][0]) + ', ' + str(preserve_list[item][1]) + ');')
        hd.write('\nTarget design: ' + record.name+'_'+operateId + '\n')
        if Ins_ERR:
            hd.write('Error report:\n')
            for item in Ins_ERR:
                hd.write(item + '\tNo feature type\n')
        if insert_number_list:
            rep_num = 0
            hd.write('Data report:\nChr\tInsert_number\n')
            for item in insert_number_list:
                hd.write(item[0] + '\t' + str(item[1]) + '\n')
                rep_num += item[1]
            hd.write('Total\t' + str(rep_num) + '\n')
    return jsonify({
        "code": 200,
        "msg": 'Success',"data":{"features":features}})
    


