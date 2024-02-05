# -*- coding: utf-8 -*-
"""
Created on Tue Jan  9 10:10:47 2024

@author: Sam_Y
"""
from Bio import SeqIO
from Bio.SeqRecord import SeqRecord
from Bio.SeqFeature import SeqFeature, CompoundLocation
import os
from GenbankUtil import newFeature
from flask import jsonify
def parse_annotation_parent(inputPath): 
    record = SeqIO.read(inputPath, 'genbank')  
    record2 = SeqRecord(record.seq)  
    record2.annotations['molecule_type'] = 'DNA'
    record2.annotations['topology'] = 'linear'
    features = {}  
    fea_merge = []  
    for fea in record.features:
        if 'Parent' in fea.qualifiers:  
            parent_id = fea.qualifiers['Parent'][0]  
            if not parent_id in features:
                features[parent_id] = {fea.type: [fea]} 
            else:
                if not fea.type in features[parent_id]:
                    features[parent_id][fea.type] = [fea]
                else:
                    features[parent_id][fea.type].append(fea)
        else:
            fea_merge.append(fea)  
    for item in features:
        for fea_type in features[item]:
            if len(features[item][fea_type]) == 1:   
                fea_merge += features[item][fea_type]
            else:  
                strings = []
                fea_loc = []
                for fea_part in features[item][fea_type]:  
                    if fea_part.qualifiers['ID'][0] in features:
                        fea_merge.append(fea_part)
                    else:
                        qua = fea_part.qualifiers 
                        strings.append(fea_part.qualifiers['ID'][0])  
                        for parts in fea_part.location.parts:  
                            if not parts in fea_loc: 
                                fea_loc.append(parts)
                if fea_loc:  
                    common_prefix = os.path.commonprefix(strings) 
                    if common_prefix[-1] in ['.', '-', '_']: 
                        common_prefix = common_prefix[:-1]
                    if len(fea_loc) == 1:  
                        sf = SeqFeature(fea_loc[0], type = fea_type, qualifiers = qua) 
                    else:  
                        if fea_loc[0].strand == 1:
                            fea_loc.sort(key = lambda x: x.start)
                        else:
                            fea_loc.sort(key = lambda x: x.start, reverse = True)
                        sf = SeqFeature(CompoundLocation(fea_loc), type = fea_type, qualifiers = qua)
                    sf.qualifiers['ID'] = [common_prefix]
                    sf.qualifiers['locus_tag'] = [common_prefix]
                    sf.qualifiers['label'] = [common_prefix]
                    fea_merge.append(sf)
    fea_merge.sort(key = lambda x: x.location.start)  

    features = []
    for feature in fea_merge:
        features.append(newFeature(feature))
    return jsonify({
        "code": 200,
        "msg": 'Success',"data":{"features":features}})
