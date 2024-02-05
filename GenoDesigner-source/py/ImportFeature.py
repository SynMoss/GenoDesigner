# -*- coding: utf-8 -*-
"""
Created on Fri Dec 29 15:54:24 2023

@author: Sam_Y
该脚本主要作用为提取其他序列中的feature并在当前序列中进行查找并标记，其他序列包括导入的genbank文件、设计和元件库等
"""
from Bio import SeqIO
from Bio.SeqFeature import SeqFeature, SimpleLocation, CompoundLocation
import re
import multiprocessing
from GenbankUtil import newFeature
from flask import jsonify
from GenoDesigner import feature_find
def importFeature(sourcePath,importPaths):
    record = SeqIO.read(sourcePath, 'genbank')
    fea_add = []
    # for seq in ['gb.all.annotations/Chr16.gb']:
    for readFilePath in importPaths:
        with open(readFilePath) as input_handle:
            for record_imp in SeqIO.parse(input_handle, "genbank"):
                pool = multiprocessing.Pool(8)
                ag = []
                for fea in record_imp.features:
                    ag.append((fea, record_imp, record))
                result = pool.map(feature_find, ag)
                for fea_list in result:
                    if fea_list:
                        for fea in fea_list:
                            if not fea in fea_add:
                                fea_add.append(fea)
    fea_add.sort(key=lambda x:x.location.start)
    for fea in record.features:
        if fea in fea_add:
            fea_add.remove(fea)
    features = []
    for feature in fea_add:
        features.append(newFeature(feature))
    return jsonify({
        "code": 200,
        "msg": 'Success',"data":{"features":features}})