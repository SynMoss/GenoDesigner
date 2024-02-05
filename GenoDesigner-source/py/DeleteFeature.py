# -*- coding: utf-8 -*-
"""
Created on Wed Dec 27 08:51:19 2023

@author: Sam_Y
Function: Delete feature + Reduce genome
"""

import pandas as pd
from Bio import SeqIO
from Bio.Seq import Seq
from Bio.SeqRecord import SeqRecord
from Bio.SeqFeature import SeqFeature, ExactPosition, SimpleLocation, CompoundLocation
from GenoDesigner import merge_intervals, remove_intersection, iv_inter, interval_intersection, feature_cut, feature_link
import multiprocessing
from flask import jsonify
from GenbankUtil import newFeature
import os
def deleteFeatureByType(inputPath,outPath,delete_list,preserve_list,operateIdLong):
    operateId = str(operateIdLong)
    Trun_ge_ERR = []
    Data_report = []
    Pos_report_list = []
    core = 4
    record = SeqIO.read(inputPath, 'genbank')
    # delete_list = ['misc_feature']
    # preserve_list = {'gene':[0, 0], 'tRNA':[0, 0], 'centromere':[0, 0]}
    #生成需删除feature区间列表
    del_iv = []
    for fea in record.features:
        if fea.type in delete_list:
            del_iv.append([int(fea.location.start), int(fea.location.end)])
        #########################################################################
    if not del_iv:
        Trun_ge_ERR.append(record.id)
    del_iv = merge_intervals(del_iv)
    del_iv = merge_intervals(del_iv)

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
    if pre_iv:
        pre_iv = merge_intervals(pre_iv)
        pre_iv = merge_intervals(pre_iv)
        deletable = [[0, pre_iv[0][0]]]
        for i in range(len(pre_iv) - 1):
            deletable.append([pre_iv[i][1], pre_iv[i+1][0]])
        deletable.append([pre_iv[-1][1], len(record.seq)])
    else:
        deletable = [[0,len(record.seq)]]
    del_fn_iv = []
    pool = multiprocessing.Pool(core)
    ag = []
    for del_region in del_iv:
        ag.append((del_region, deletable))
    result = pool.map(iv_inter, ag)
    for del_fn_region in result:
        del_fn_iv += del_fn_region
    result = None
    ag = None
    del_fn_iv = merge_intervals(del_fn_iv)
    if len(del_fn_iv)==0:
        return jsonify({
        "code": 500,
        "msg": 'Script failed:del_fn_iv is empty'})
    s = 0
    pre_fn_iv = [[0, del_fn_iv[0][0], s]]
    for i in range(1, len(del_fn_iv)):
        s += del_fn_iv[i-1][1] - del_fn_iv[i-1][0]
        pre_fn_iv.append([del_fn_iv[i-1][1], del_fn_iv[i][0], s])
    s+= del_fn_iv[-1][1] - del_fn_iv[-1][0]
    pre_fn_iv.append([del_fn_iv[-1][1], len(record.seq), s])
    print('pre_iv:',pre_iv)
    print('pre_fn_iv:',pre_fn_iv)
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
    pool.close()        
    pool.join()        
    record_del = SeqRecord(Seq(''))
    record_del.annotations['molecule_type'] = 'DNA'
    record_del.annotations['topology'] = 'linear'
    ag = []
    for i in range(core):
        ag.append([record, pre_fn_iv[int(i*len(pre_fn_iv)/core):int((i + 1)*len(pre_fn_iv)/core)]])
    ag[-1][1] += pre_fn_iv[int((i + 1)*len(pre_fn_iv)/core):]
    pool = multiprocessing.Pool(core)
    result = pool.map(feature_link, ag)
    pool.close()        
    pool.join()         
    result.sort(key = lambda x: x[1])
    ag = None
    for part in result:
        record_del += part[0]
    result = None
    record_del.features+=sf_add
    record_del.features.sort(key=lambda x: x.location.start)
    record_del.annotations['molecule_type'] = 'DNA'
    record_del.annotations['topology'] = 'linear'
    features = []
    for feature in record_del.features:
        features.append(newFeature(feature))
    with open(outPath, 'w') as f:
        f.write(str(record_del.seq))
    #########################################################################
    Data_report.append([record.id, len(record.seq) - len(record_del.seq),len(record_del.seq)])
    del_df = pd.DataFrame(del_fn_iv, columns = ['Delete_start', 'Delete_end'])
    if del_fn_iv:
        del_df['Delete_length'] = del_df['Delete_end'] - del_df['Delete_start']
        del_df.loc[:, 'Chr'] = record.id
        del_df['Delete_start'] += 1
    else:
        del_df.insert(0,"Chr",[])
        del_df.insert(3,"Delete_length",[])
    del_df = del_df[['Chr', 'Delete_start', 'Delete_end', 'Delete_length']]
    folderName = os.path.dirname(outPath)
    del_df.to_excel(folderName+"/"+operateId+'.xlsx',"sheet1",index=False,engine='xlsxwriter')
    with open(folderName+"/"+operateId+'.txt', 'w') as hd:
        hd.write('Function: Delete by Feature Type\nArgument:\nFeature to delete:')
        for item in delete_list:
            hd.write(' ' + item + ';')
        hd.write('\nFeature to preserve: ')
        if preserve_list:
            for item in preserve_list:
                hd.write(' ' + item + '(' + str(preserve_list[item][0]) + ', ' + str(preserve_list[item][1]) + ');')
        else:
            hd.write('None')
        hd.write('\nTarget design: ' + record.id+'_'+operateId + '\n')    
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
# if __name__ == '__main__':
    # path = Path('F:\\download\\gb\\test.gb')
    # print(path.dirname)
    deleteFeatureByType('F:\\download\\gb\\test.gb','F:\\download\\gb\\test_delete_feature_out.gb',['gene'])