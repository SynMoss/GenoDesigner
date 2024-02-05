# -*- coding: utf-8 -*-
"""
Created on Fri Dec 29 16:29:05 2023

@author: Sam_Y
"""

import pandas as pd
from Bio import SeqIO
from Bio.Seq import Seq
from Bio.SeqRecord import SeqRecord
from Bio.SeqFeature import SeqFeature, ExactPosition, SimpleLocation, CompoundLocation
from GenoDesigner import is_overlap, merge_intervals,getName
from flask import jsonify
from GenbankUtil import newFeature
import os
def codonReplace(inputPath,outPath,codon_del,codon_replace,operateIdLong):
    operateId = str(operateIdLong)
    cnum = '01'
    record = SeqIO.read(inputPath, 'genbank')
    cds_dict = {'TAG':[],
                'TGA':[],
                'TAA':[],
                'ERR':[]}
    cds_range_list = []
    # codon_del = ['TAG', 'TGA']
    # codon_replace = 'TAA'
    for fea in record.features:
        if fea.type == 'CDS':
            st_co = str(fea.extract(record.seq)[-3:]).upper() 
            if st_co in ['TAG', 'TGA', 'TAA']: 
                if not fea in cds_dict[st_co]:  
                    cds_dict[st_co].append(fea)  
                    if len(fea.location.parts) == 1:
                        cds_range_list.append([fea.location.start + int((1-fea.location.strand) * 1.5), fea.location.end - int((1+fea.location.strand) * 1.5)])
                    else:
                        for i in range(len(fea.location.parts) - 1):
                            cds_range_list.append([fea.location.parts[i].start, fea.location.parts[i].end])
                        if fea.location.strand == 1:
                            cds_range_list.append([fea.location.parts[-1].start, fea.location.parts[-1].end - 3])
                        else:
                            cds_range_list.append([fea.location.parts[-1].start + 3, fea.location.parts[-1].end])
            else:  
                cds_dict['ERR'].append([getName(fea.qualifiers), fea.location.strand, int(fea.location.start), int(fea.location.end), str(fea.extract(record.seq)[-3:].upper())])
    cds_range_list = merge_intervals(cds_range_list)
    cds_range_list = merge_intervals(cds_range_list)
    replace_info_list = []
    replace_ERR_pos_list = []
    codon_number_list = []
    Rep_cod_ERR = []
    other_ERR = []
    for st_del in codon_del:
        for fea in cds_dict[st_del]:
            if fea.location.strand == 1:
                st_pos = (fea.location.end - 3, fea.location.end) 
                cod = codon_replace
            else:
                st_pos = (fea.location.start, fea.location.start + 3)
                cod = str(Seq(codon_replace).reverse_complement())
            featureName = getName(fea.qualifiers)
            if is_overlap(st_pos, cds_range_list):
                replace_ERR_pos_list.append([featureName, fea.location.strand, int(fea.location.start), int(fea.location.end), st_del, int(st_pos[0]), int(st_pos[1])])
            else:
                replace_info_list.append([featureName, fea.location.strand, int(fea.location.start), int(fea.location.end), st_del, int(st_pos[0]), int(st_pos[1]), cod])
    replace_pos_list = []
    for item in replace_info_list:
        if not [item[5], item[6]] in replace_pos_list: 
            replace_pos_list.append([item[5], item[6]])
            sf = SeqFeature(SimpleLocation(item[5], item[6], strand = item[1]), type = 'stop') 
            sf.qualifiers['locus_tag'] = [str(item[4]) + '>' + codon_replace]
            sf.qualifiers['label'] = [str(item[4]) + '>' + codon_replace]
            record.features.append(sf)
            record.seq = record.seq[:item[5]] + Seq(item[7]) + record.seq[item[6]:]
    record.annotations['molecule_type'] = 'DNA'
    record.annotations['topology'] = 'linear'
    # SeqIO.write(record, 'codon_replace/Chr' + cnum + '.rm_TE.rm_inter.cod_rep.gb', "genbank")
    replace_ERR_pos_list_count = []
    for item in replace_ERR_pos_list:
        if not [item[5], item[6]] in replace_ERR_pos_list_count:
            replace_ERR_pos_list_count.append([item[5], item[6]])
    print(cds_dict)
    features = []
    for feature in record.features:
        features.append(newFeature(feature))
    with open(outPath, 'w') as f:
        f.write(str(record.seq))
    # if replace_info_list:
    #     codon_number_list.append([record.id, str(len(replace_pos_list)), str(len(replace_ERR_pos_list_count))])
        #print('Replaced a total of ' + str(len(replace_pos_list)) + ' stop codons, ' + str(len(replace_ERR_pos_list_count)) + ' preserved (stop codons at same position are counted as one)')
    folderName = os.path.dirname(outPath)
    st_ERR_df = pd.DataFrame(cds_dict['ERR'], columns = ['Name', 'Strand', 'Start', 'End', 'Stop_codon'])
    if cds_dict['ERR']:
        st_ERR_df.loc[:, 'Start'] += 1
        st_ERR_df.loc[:, 'Chr'] = record.id
    else:
        st_ERR_df.insert(0,'Chr',[])
    st_ERR_df = st_ERR_df[['Chr', 'Name', 'Strand', 'Start', 'End', 'Stop_codon']]
    replace_ERR_df = pd.DataFrame(replace_ERR_pos_list, columns = ['Name', 'Strand', 'Start', 'End', 'Stop_codon', 'Stop_codon_start', 'Stop_codon_end'])
    if replace_ERR_pos_list:
        replace_ERR_df.loc[:, 'Start'] += 1
        replace_ERR_df.loc[:, 'Chr'] = record.id
    else:
        replace_ERR_df.insert(0,'Chr',[])
    replace_ERR_df = replace_ERR_df[['Chr', 'Name', 'Strand', 'Start', 'End', 'Stop_codon']]
        #replace_ERR_df.to_csv('st_report/Chr' + cnum + '.replace.ERR.csv', index = None)
    replace_df = pd.DataFrame(replace_info_list, columns = ['Name', 'Strand', 'Start', 'End', 'Stop_codon', 'Stop_codon_start', 'Stop_codon_end', 'Codon_replace'])
    if replace_info_list:
        codon_number_list.append([record.id, str(len(replace_pos_list)), str(len(replace_ERR_pos_list_count))])
        #print('Replaced a total of ' + str(len(replace_pos_list)) + ' stop codons, ' + str(len(replace_ERR_pos_list_count)) + ' preserved (stop codons at same position are counted as one)')
        replace_df.loc[:, 'Codon_replace'] = codon_replace
        replace_df = replace_df.sort_values(by='Start', ascending=True)
        replace_df.loc[:, 'Start'] += 1
        replace_df.loc[:, 'Chr'] = record.id
    else:
        replace_df.insert(0,'Chr',[])
    replace_df = replace_df[['Chr', 'Name', 'Strand', 'Start', 'End', 'Stop_codon']]
    with pd.ExcelWriter(folderName+"/"+operateId+'.xlsx',engine='xlsxwriter') as writer:
        replace_df.to_excel(writer,sheet_name="replace_data",index=False)
        replace_ERR_df.to_excel(writer,sheet_name="replace_ERR",index=False)
        st_ERR_df.to_excel(writer,sheet_name="stop_codon_ERR",index=False)
    with open(folderName+"/"+operateId+'.txt', 'w') as hd:
        hd.write('Function: Replace Stop Codons\nArgument:\nCodon to be replace:')
        for item in codon_del:
            hd.write(' ' + item + ';')
        hd.write('\nCodon for replacement: ' + codon_replace)
        hd.write('\nTarget design: ' + record.name+'_'+operateId + '\n')
        if Rep_cod_ERR:
            hd.write('Error report:\n')
            for item in Rep_cod_ERR:
                hd.write(item + '\tNo CDS\n')
        if codon_number_list:
            codon_rep_num = 0
            codon_pre_num = 0
            hd.write('Data report: (stop codons at same positions are counted once)\nChr\tCodon_replaced\tCodon_preserved\n')
            for item in codon_number_list:
                hd.write(item[0] + '\t' + item[1] + '\t' + item[2] + '\n')
                codon_rep_num += int(item[1])
                codon_pre_num += int(item[2])
            hd.write('Total\t' + str(codon_rep_num) + '\t' + str(codon_pre_num) + '\n')
    return jsonify({
        "code": 200,
        "msg": 'Success',
        "data":{"features":features}})
    # st_ERR_df = None
    # if cds_dict['ERR']:
    # st_ERR_df = pd.DataFrame(cds_dict['ERR'], columns = ['Name', 'Strand', 'Start', 'End', 'Stop_codon'])
    # if replace_ERR_pos_list:
    #     replace_ERR_df = pd.DataFrame(replace_ERR_pos_list, columns = ['Name', 'Strand', 'Start', 'End', 'Stop_codon'])
    # if replace_info_list:
    #     print('Replaced a total of ' + str(len(replace_pos_list)) + ' stop codons (CDS with the same stop codon position is counted as one)')
    #     replace_df = pd.DataFrame(replace_info_list, columns = ['Name', 'Strand', 'Start', 'End', 'Stop_codon', 'Stop_codon_start', 'Stop_codon_end', 'Codon_replace'])
    #     replace_df.loc[:, 'Codon_replace'] = codon_replace
    #     replace_df = replace_df.sort_values(by='Start', ascending=True)


