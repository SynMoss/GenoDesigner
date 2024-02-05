from Bio.SeqRecord import SeqRecord
from Bio.SeqFeature import SeqFeature, ExactPosition, SimpleLocation, CompoundLocation
from Bio.Seq import Seq
import re
import multiprocessing
def is_overlap(single_interval, join_intervals):
    intervals = sorted(join_intervals, key=lambda x: x[0])
    for iv in intervals:
        if not(iv[1] <= single_interval[0] or iv[0] >= single_interval[1]):
            return True
    return False
def feature_find(x): 
    fea = x[0]
    donor_record = x[1]
    target_record = x[2]
    fea_add = []
    st = fea.location.strand
    mas = 1*st
    fea_tmp = SeqFeature(SimpleLocation(fea.location.start, fea.location.end, strand=fea.location.strand), type = fea.type, qualifiers = fea.qualifiers)
    for match in re.finditer(str(fea_tmp.extract(donor_record.seq)).upper(), str(target_record.seq).upper()):  
        fea_loc = []
        for part in fea.location.parts:  
            fea_loc.append(SimpleLocation(  
                int((1+mas)/2*part.start - (1-mas)/2*part.end - (1+mas)/2*fea.location.start + (1-mas)/2*fea.location.end + match.start()),
                int(-(1-mas)/2*part.start + (1+mas)/2*part.end - (1+mas)/2*fea.location.start + (1-mas)/2*fea.location.end + match.start()),
                strand = 1  
            ))
        if len(fea_loc) == 1:  
            fea_add.append(SeqFeature(fea_loc[0], type = fea.type, qualifiers = fea.qualifiers))
        else:  
            fea_add.append(SeqFeature(CompoundLocation(fea_loc), type = fea.type, qualifiers = fea.qualifiers))
    mas = -1*st
    for match in re.finditer(str(fea_tmp.extract(donor_record.seq).reverse_complement()).upper(), str(target_record.seq).upper()):
        fea_loc = []
        for part in fea.location.parts:
            fea_loc.append(SimpleLocation(
                int((1+mas)/2*part.start - (1-mas)/2*part.end - (1+mas)/2*fea.location.start + (1-mas)/2*fea.location.end + match.start()),
                int(-(1-mas)/2*part.start + (1+mas)/2*part.end - (1+mas)/2*fea.location.start + (1-mas)/2*fea.location.end + match.start()),
                strand = -1
            ))
        if len(fea_loc) == 1:
            fea_add.append(SeqFeature(fea_loc[0], type = fea.type, qualifiers = fea.qualifiers))
        else:
            fea_add.append(SeqFeature(CompoundLocation(fea_loc), type = fea.type, qualifiers = fea.qualifiers))
    return fea_add

def merge_intervals(intervals):
    intervals.sort(key=lambda x: x[0])
    merged = []
    for interval in intervals:
        if not merged or merged[-1][1] < interval[0]:
            merged.append(interval)
        else:
            merged[-1] = (merged[-1][0], max(merged[-1][1], interval[1]))
    return merged
def remove_intersection(interval1, interval2):
    start1, end1 = interval1
    start2, end2 = interval2
    if start1 >= start2 and end1 <= end2:
        return []
    if start1 <= start2 and end1 >= end2:
        return [(start1, start2), (end2, end1)]
    if start1 <= start2 and end1 <= end2:
        return [(start1, start2)]
    if start1 >= start2 and end1 >= end2:
        return [(end2, end1)]
def iv_inter(x):
    interval1, intervals2 = x
    ov = []
    for iv in intervals2:
        start = max(interval1[0], iv[0])
        end = min(interval1[1], iv[1])
        if start <= end:
            ov.append([start, end])
    return ov
def interval_intersection(x):
    interval1, intervals2 = x
    ov = []
    for iv in intervals2:
        start = max(interval1[0], iv[0])
        end = min(interval1[1], iv[1])
        if start <= end:
            ov.append([start, end, iv[2]])
    return ov


def feature_cut(x):
    fea, pre_fn_iv = x 
    overlap_iv = []
    fea_loc_iv = []
    for pre_fn_region in pre_fn_iv:
        if not(fea.location.end <= pre_fn_region[0] or fea.location.start >= pre_fn_region[1]):
            if not (fea.location.start >= pre_fn_region[0] and fea.location.end <= pre_fn_region[1]):
                overlap_iv.append(pre_fn_region)  
    if overlap_iv:
        for loc in fea.location.parts: 
            fea_loc = interval_intersection(((loc.start, loc.end), overlap_iv))
            for item in fea_loc:
                fea_loc_iv.append([item[0] - item[2], item[1] - item[2], item[2]])
        if len(fea_loc_iv) == 1: 
            sf = SeqFeature(SimpleLocation(fea_loc_iv[0][0], fea_loc_iv[0][1], strand = fea.location.strand), type = fea.type, qualifiers = fea.qualifiers)
        elif len(fea_loc_iv) > 1: 
            cl = []  
            for fea_loc in fea_loc_iv:
                cl.append(SimpleLocation(fea_loc[0], fea_loc[1], strand = fea.location.strand))
            if fea.location.strand == 1: 
                cl.sort(key=lambda x: x.start)
            else:
                cl.sort(key=lambda x: x.start, reverse = True)
            sf = SeqFeature(CompoundLocation(cl), type = fea.type, qualifiers = fea.qualifiers)
        else:
            return
        name = getName(sf.qualifiers)
        sf.qualifiers['locus_tag'] = [name+ '.CUT']
        if 'label' in sf.qualifiers:
            sf.qualifiers['label'] = [name+ '.CUT']
        if 'note' in sf.qualifiers:
            sf.qualifiers['note'].append('Part of feature ' + name)
        else:
            sf.qualifiers['note'] = ['Part of feature ' + name]
        return sf
def feature_split(x):
    fea, insert_iv_list = x  
    overlap_iv = []
    fea_loc_iv = []
    for insert_iv_region in insert_iv_list: 
            if not(fea.location.end <= insert_iv_region[0] or fea.location.start >= insert_iv_region[1]):
                if not (fea.location.start >= insert_iv_region[0] and fea.location.end <= insert_iv_region[1]):
                    overlap_iv.append(insert_iv_region)
    if overlap_iv:
        for loc in fea.location.parts:  
            fea_loc = interval_intersection(((loc.start, loc.end), overlap_iv))
            for item in fea_loc:
                fea_loc_iv.append([item[0] + item[2], item[1] + item[2], item[2]])
        if len(fea_loc_iv) > 1:
            cl = []  
            for fea_loc in fea_loc_iv:
                cl.append(SimpleLocation(fea_loc[0], fea_loc[1], strand = fea.location.strand))
            if fea.location.strand == 1:  
                cl.sort(key=lambda x: x.start)
            else:
                cl.sort(key=lambda x: x.start, reverse = True)
            sf = SeqFeature(CompoundLocation(cl), type = fea.type, qualifiers = fea.qualifiers)
        else:
            return
        name = getName(sf.qualifiers)
        sf.qualifiers['locus_tag'] = [name+ '.SPLIT']
        sf.qualifiers['label'] = [name+ '.SPLIT']
        if 'note' in sf.qualifiers:
            sf.qualifiers['note'].append('Split ' + name)
        else:
            sf.qualifiers['note'] = ['Split ' + name]
        return sf

def feature_not_split(x):
    fea, insert_iv_list = x  
    overlap_iv = []
    fea_loc_iv = []
    for insert_iv_region in insert_iv_list: 
            if not(fea.location.end <= insert_iv_region[0] or fea.location.start >= insert_iv_region[1]):
                if not (fea.location.start >= insert_iv_region[0] and fea.location.end <= insert_iv_region[1]):
                    overlap_iv.append(insert_iv_region)
    if overlap_iv:
        for loc in fea.location.parts:  
            for overlap_region in overlap_iv:
                if loc.start >= overlap_region[0] and loc.start <= overlap_region[1]:
                    loc_s = loc.start + overlap_region[2]
                if loc.end >= overlap_region[0] and loc.end <= overlap_region[1]:
                    loc_e = loc.end + overlap_region[2]
            fea_loc_iv.append([loc_s, loc_e])
        if len(fea_loc_iv) == 1:  
            sf = SeqFeature(SimpleLocation(fea_loc_iv[0][0], fea_loc_iv[0][1], strand = fea.location.strand), type = fea.type, qualifiers = fea.qualifiers)
        elif len(fea_loc_iv) > 1:
            cl = []  
            for fea_loc in fea_loc_iv:
                cl.append(SimpleLocation(fea_loc[0], fea_loc[1], strand = fea.location.strand))
            if fea.location.strand == 1:  
                cl.sort(key=lambda x: x.start)
            else:
                cl.sort(key=lambda x: x.start, reverse = True)
            sf = SeqFeature(CompoundLocation(cl), type = fea.type, qualifiers = fea.qualifiers)
        else:
            return
        name = getName(sf.qualifiers)
        sf.qualifiers['locus_tag'] = [name+ '.INSERT']
        sf.qualifiers['label'] = [name+ '.INSERT']
        if 'note' in sf.qualifiers:
            sf.qualifiers['note'].append('Inserted ' + name)
        else:
            sf.qualifiers['note'] = ['Inserted ' + name]
        return sf
def feature_link(x):
    record_link= SeqRecord(Seq(''))
    record_link.annotations['molecule_type'] = 'DNA'
    record_link.annotations['topology'] = 'linear'
    record, pre_fn_iv = x 
    print("pre_fn_iv2:",pre_fn_iv)
    start = pre_fn_iv[0][0]
    for iv in pre_fn_iv:
        record_link += record[iv[0]:iv[1]]
    return [record_link, start]
def seq_insert(x):
    record_insert= SeqRecord(Seq(''))
    record_insert.annotations['molecule_type'] = 'DNA'
    record_insert.annotations['topology'] = 'linear'
    record, insert_iv_list, ins_rec = x  
    start = insert_iv_list[1][0]
    for iv in insert_iv_list:
        record_insert += record[iv[0]:iv[1]]
        if iv[3] == 1:
            record_insert += ins_rec
        elif iv[3] == -1:
            record_insert += ins_rec.reverse_complement()
    return [record_insert, start]
def getName(qualifiers):
    if 'ID' in qualifiers:
        return qualifiers['ID'][0]
    elif 'Name' in qualifiers:
        return qualifiers['Name'][0]
    elif 'locus_tag' in qualifiers:
        return qualifiers['locus_tag'][0]
    elif 'label' in qualifiers:
        return qualifiers['label'][0]
    elif 'gene' in qualifiers:
        return qualifiers['gene'][0]
    elif 'ApEinfo_label' in qualifiers:
        return qualifiers['ApEinfo_label'][0]
    elif 'name' in qualifiers:
        return qualifiers['name'][0]
    elif 'organism' in qualifiers:
        return qualifiers['organism'][0]
    elif 'note' in qualifiers:
        return qualifiers['note'][0]
    else:
        return "Untitled Feature"
if __name__ == '__main__':
    print('test')  