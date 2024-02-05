import pandas as pd
from Bio import SeqIO
from Bio.Seq import Seq
from Bio.SeqRecord import SeqRecord
from Bio.SeqFeature import SeqFeature, ExactPosition, SimpleLocation, CompoundLocation
from GenoDesigner import merge_intervals, remove_intersection, iv_inter, interval_intersection, feature_cut, feature_link
from flask import jsonify
from GenbankUtil import newFeature
import multiprocessing
import os
def reduceGenome(inputPath,outPath,preserve_list,operateIdLong):
    operateId = str(operateIdLong)
    Trun_ge_ERR = []
    Data_report = []
    Pos_report_list = []
    
    core = 4
    
    record = SeqIO.read(inputPath, 'genbank')
    pre_iv = []
    for fea in record.features:
        if fea.type in preserve_list:
            pre_range = [int(fea.location.start) - preserve_list[fea.type][int((1- fea.location.strand)/2)], int(fea.location.end) + preserve_list[fea.type][int((1 + fea.location.strand)/2)]]
            if pre_range[0] < 0:
                pre_range[0] = 0
            elif pre_range[0] > len(record.seq):
                pre_range[0] = len(record.seq)
            if pre_range[1] < 0:
                pre_range[1] = 0
            elif pre_range[1] > len(record.seq):
                pre_range[1] = len(record.seq)
            pre_iv.append(pre_range)
    pre_iv = merge_intervals(pre_iv)
    pre_iv = merge_intervals(pre_iv)
    pos = 0
    s = 0
    pre_fn_iv = []
    for pre_pos in pre_iv:
        s += pre_pos[0] - pos
        pre_fn_iv.append([pre_pos[0], pre_pos[1], s])
        pos = pre_pos[1]

    sf_add = []
    pool = multiprocessing.Pool(core)
    ag = []
    for fea in record.features:
        ag.append((fea, pre_fn_iv))
    result = pool.map(feature_cut, ag)
    for sf in result:
        if sf:
            sf_add.append(sf)
    result = None
    ag = None
    record_reduce = SeqRecord(Seq(''))
    record_reduce.annotations['molecule_type'] = 'DNA'
    record_reduce.annotations['topology'] = 'linear'

    ag = []
    for i in range(core):
        ag.append([record, pre_fn_iv[int(i*len(pre_fn_iv)/core):int((i + 1)*len(pre_fn_iv)/core)]])
    ag[-1][1] += pre_fn_iv[int((i + 1)*len(pre_fn_iv)/core):]
    pool = multiprocessing.Pool(core)
    result = pool.map(feature_link, ag)
    result.sort(key = lambda x: x[1])
    ag = None
    for part in result:
        record_reduce += part[0]
    result = None
    record_reduce.features+=sf_add
    record_reduce.features.sort(key=lambda x: x.location.start)
    record_reduce.annotations['molecule_type'] = 'DNA'
    record_reduce.annotations['topology'] = 'linear'
    features = []
    for feature in record_reduce.features:
        features.append(newFeature(feature))
    with open(outPath, 'w') as f:
        f.write(str(record_reduce.seq))
    Data_report.append([record.id, len(record.seq) - len(record_reduce.seq),len(record_reduce.seq)])
    pres_df = pd.DataFrame(pre_fn_iv, columns = ['Preserve_start', 'Preserve_end', 'Preserve_length'])
    if pre_fn_iv:
        pres_df['Preserve_length'] = pres_df['Preserve_end'] - pres_df['Preserve_start']
        pres_df.loc[:, 'Chr'] = record.id
        pres_df['Preserve_start'] += 1
    else:
        pres_df.insert(0,'Chr',[])
    pres_df = pres_df[['Chr', 'Preserve_start', 'Preserve_end', 'Preserve_length']]
    folderName = os.path.dirname(outPath)
    pres_df.to_excel(folderName+"/"+operateId+'.xlsx',"sheet1",index=False,engine='xlsxwriter')
    Pos_report_list.append(pres_df)
    with open(folderName+"/"+operateId+'.txt', 'w') as hd:
        hd.write('Function: Truncate Genome\nArgument:\nFeature to preserve:')
        for item in preserve_list:
            hd.write(' ' + item + '(' + str(preserve_list[item][0]) + ', ' + str(preserve_list[item][1]) + ');')
        hd.write('\nTarget design: ' + record.name+'_'+operateId + '\n')
        if Trun_ge_ERR:
            hd.write('Error report:\n')
            for item in Trun_ge_ERR:
                hd.write(item + '\tNo feature type\n')
        if Data_report:
            delete_len = 0
            preserve_len = 0
            hd.write('Data report:\nChr\tDelete_length\tRest_length\n')
            for item in Data_report:
                hd.write(item[0] + '\t' + str(item[1]) + '\t' + str(item[2]) + '\n')
                delete_len += item[1]
                preserve_len += item[2]
            hd.write('Total\t' + str(delete_len) + '\t' + str(preserve_len) + '\n')
    return jsonify({
        "code": 200,
        "msg": 'Success',"data":{"features":features}})