#!/usr/bin/env python
# -*- coding: utf-8 -*-
from __future__ import print_function

from Bio import SeqIO
import json
import time
# from Bio.SeqFeature import CompoundLocation
# from Bio.SeqFeature import SimpleLocation
# gb_seq = SeqIO.parse("D:\\test-feature.gbff", "genbank")
def newSequence(record):
    fileName = str(time.time())+"_"+ record.name+".gb"
    s = {"fileName":fileName,"bpLength":len(record.seq),"features":[],"name":record.name}
    if hasattr(record,'annotations'):
        # print(record.annotations)
        s['gbDivision']=record.annotations.get('data_file_division',"")
        s['date']=record.annotations.get('date','')
        s['sequenceTypeFromLocus']=record.annotations.get('molecule_type','')
        if 'keywords' in record.annotations and len(record.annotations['keywords'])>0:
            s['keyWords']=record.annotations['keywords'][0]
            s['extraLines']=['KEYWORDS    '+s['keyWords']]
        if 'topology' in record.annotations and record.annotations['topology'].lower()=='circular':
            s['circular']=True
        else:
            s['circular']=False
    return s
def newFeature(record):
    locations=[]
    feature = {"strand":record.strand,"type":record.type,"notes":record.qualifiers,"locations":locations,"operator":""}
    if(hasattr(record.location,"operator")):
        feature["operator"] = record.location.operator
    # print(record.location.parts)
    # print(record.location)
    for part in record.location.parts:
        locations.append({"start":part.start,"end":part.end-1,"strand":part.strand})
    if part.strand<0:
        locations.reverse()
    return feature
#'annotations', 'count', 'dbxrefs', 'description', 'features', 'format', 'id', 'islower', 'isupper', 'letter_annotations', 'lower', 'name', 'reverse_complement', 'seq', 'translate', 'upper'
#'id', 'location', 'location_operator', 'qualifiers', 'ref', 'ref_db', 'strand', 'translate', 'type'
def cutGbFile(writeFileFolder,readFilePath):
    result=[]
    with open(readFilePath) as input_handle:
        for record in SeqIO.parse(input_handle, "genbank"):
            s = newSequence(record)
            fileName = s['fileName']
            # print(record.annotations)
            # print(record.features[1])
            # print(record.features[2])
            result.append(s)
            for feature in record.features:
                s['features'].append(newFeature(feature))
            with open(writeFileFolder+"/"+fileName, "w") as output_handle:
                output_handle.write(str(record.seq))
                # SeqIO.write([record], output_handle, "genbank")
    return result
def cutFaFile(writeFileFolder,readFilePath):
    result=[]
    with open(readFilePath) as input_handle:
        for record in SeqIO.parse(input_handle, "fasta"):
            print(record)
            s = newSequence(record)
            fileName = s['fileName']
            result.append(s)
            with open(writeFileFolder+"/"+fileName, "w") as output_handle:
                output_handle.write(str(record.seq))
                # SeqIO.write([record], output_handle, "genbank")
    return result
# cutGbFile("D:\\","D:\\test23.gb")
# with open("D:\\test23.gbff") as input_handle:
#     for record in SeqIO.parse(input_handle, "genbank"):
#         fileName = record.name
#         print("fileName:"+fileName)
#         print(type(record.features[0]))
#         for feature in record.features:
#             print(type(feature.location))
#             print(dir(feature.location))
#         # print(type(record))
#         # print(dir(record.seq))
#         # record.features = []
#         # writeGbFile([record])
#         # print(dir(record.seq))
