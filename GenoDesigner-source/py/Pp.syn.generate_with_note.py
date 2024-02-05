"""
Author: Wenfei Yu
Description: This python script is bound for generation of synthetic genomic sequences for synMoss
Note: require 3 input arguments: directory of chromosome genbank file; chromosome number; chromosome arm (L or R)
Input files: centromere annotation in BED format; chromosome genbank file with annotation of TE, gene and CDS; PCRmark information in BED format
"""


import pandas as pd
from Bio import SeqIO
from Bio.Seq import Seq
from Bio.SeqRecord import SeqRecord
from Bio.SeqFeature import SeqFeature, FeatureLocation, ExactPosition, SimpleLocation
import os
import argparse
if not os.path.exists('syn/'):
    os.mkdir('syn/')
parser = argparse.ArgumentParser()
parser.add_argument('-g', '--gb_file', type=str, default=None)
parser.add_argument('-n', '--chr', type=int, default=None)
parser.add_argument('-a', '--arm',  type=str, default=None)
parser.add_argument('-c', '--cen_file', type=str, default='Centromere.bed')
parser.add_argument('-p', '--primer_file', type=str, default=None)
parser.add_argument('-u', '--usage', action='store_true', required=False)
parser.add_argument('--clean', action='store_true', required=False)
args=parser.parse_args()
if not (args.gb_file and args.chr and args.arm) or args.usage:
    exit('Usage: python Pp.syn.generate.py -g <Genbank_file_path> -n <Chr_number(INT)> -a <L/R> -p <PCRmark_file_path>\nOptions and arguments:\n-g, --gb_file\t\tchromosome genbank file\n-n, --chr\t\tchromosome number (1, 2...)\n-a, --arm\t\tspecify chromosome arm to design (L or R)\n-p, --primer_file\tPCRmark information for the chromosome arm\n-c, --cen\t\tcentromere file in bed format, default as Centromere.bed\n--clean\t\t\tnot generate semi-designed sequence files\n-u, --usage\t\tprint usage')
genbank_file = args.gb_file
Chr_num = args.chr
arm = args.arm
cen = pd.read_csv(args.cen_file, sep = '\t')   ##read centromere data
primer_file=args.primer_file
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
        return None
    if start1 <= start2 and end1 >= end2:
        return (start1, end1)
    if start1 <= start2 and end1 <= end2:
        return (start1, start2)
    if start1 >= start2 and end1 >= end2:
        return (end2, end1)
if Chr_num < 10:
    chromosome = "Chr0" + str(Chr_num)
else:
    chromosome = "Chr" + str(Chr_num)
if not primer_file:
    primer_file=chromosome + arm + "_PCRmark_info.bed"
Primer_df = pd.read_csv(primer_file, sep = '\t')   ##read PCRmark data
record = SeqIO.read(genbank_file, 'genbank')
if arm == 'L':
    record_arm = record[:cen.loc[Chr_num - 1, 'Start'] - 1]
elif arm == 'R':
    record_arm = record[cen.loc[Chr_num - 1, 'End']:]
    for indexs in Primer_df.index:
        Primer_df.loc[indexs, 'start'] -= cen.loc[Chr_num - 1, 'End']
        Primer_df.loc[indexs, 'end'] -= cen.loc[Chr_num - 1, 'End']
if not args.clean:
    SeqIO.write(record_arm, 'syn/' + chromosome + arm + '.gb', "genbank")
for indexs in Primer_df.index:
    p_gene = Primer_df.loc[indexs, "gene"] 
    p_start = Primer_df.loc[indexs, "start"]  
    p_end = Primer_df.loc[indexs, "end"]  
    p_strand = Primer_df.loc[indexs, "strand"] 
    wt_seq = Primer_df.loc[indexs, "wt_tag"]  
    p_seq = Primer_df.loc[indexs, "syn_tag"] 
    if p_strand == '+':  
        p_strand = 1  
        replace_seq = Seq(p_seq)  
        wt_seq = Seq(wt_seq)  
        p_name = p_gene + '.F'  
    else:
        p_strand = -1 
        replace_seq = Seq(p_seq).reverse_complement() 
        wt_seq = Seq(wt_seq).reverse_complement()
        p_name = p_gene + '.R'
    record_arm.seq = record_arm.seq[:p_start] + replace_seq + record_arm.seq[p_end:]
    sf = SeqFeature(SimpleLocation(ExactPosition(p_start), ExactPosition(p_end), strand = p_strand), type = 'primer_bind')
    sf.qualifiers['locus_tag'] = p_name.split('.')[0] + '.syn.' + p_name.split('.')[1]
    sf.qualifiers['label'] = sf.qualifiers['locus_tag']
    record_arm.features.append(sf) 
record_arm.annotations['molecule_type'] = 'DNA'
record_arm.annotations['topology'] = 'linear' 
if not args.clean: 
    SeqIO.write(record_arm, 'syn/' + chromosome + arm + '.with_syn_PCRmark.gb', "genbank")
te = []
for fea in record_arm.features:
    if fea.type == 'misc_feature':
        te.append([int(fea.location.start), int(fea.location.end)])
te = merge_intervals(te)
te = merge_intervals(te)
ge = []
for fea in record_arm.features:
    if fea.type == 'gene':
        ge.append([int(fea.location.start), int(fea.location.end)])
ge = merge_intervals(ge)
te_remove = []
for te_region in te:
    cp = 0
    for gene_region in ge:
        if not(te_region[1] <= gene_region[0] or te_region[0] >= gene_region[1]): 
            cp = 1
            te_s = remove_intersection(te_region, gene_region)  
            if te_s:  
                te_remove.append(te_s)  
            else:
                break
    if cp == 0:  
        te_remove.append(te_region)
te_remove = merge_intervals(te_remove)
te_pos = [0]
for pos in te_remove:
        te_pos.append(pos[0])
        te_pos.append(pos[1])
te_pos.append(len(record_arm.seq))
record_syn_rm_te = SeqRecord(Seq(''))
record_syn_rm_te.annotations['molecule_type'] = 'DNA'
record_syn_rm_te.annotations['topology'] = 'linear'
for i in range(int(len(te_pos) / 2)):
    record_syn_rm_te += record_arm[te_pos[2 * i]:te_pos[2 * i + 1]]
record_syn_rm_te.annotations['molecule_type'] = 'DNA'
record_syn_rm_te.annotations['topology'] = 'linear'
if not args.clean: 
    SeqIO.write(record_syn_rm_te, 'syn/' + chromosome + arm + '.with_syn_PCRmark.remove_TE.gb', "genbank")
gene_iv = []
record_syn_rm_te_rm_in = SeqRecord(Seq(''))
record_syn_rm_te_rm_in.annotations['molecule_type'] = 'DNA'
record_syn_rm_te_rm_in.annotations['topology'] = 'linear'
for fea in record_syn_rm_te.features:
    if fea.type == 'gene':
        gene_iv.append([int(fea.location.start)- 2500 - 500 * fea.location.strand, int(fea.location.end)+ 2500 - 500 * fea.location.strand])
gene_iv = merge_intervals(gene_iv)
for iv in gene_iv:
    record_syn_rm_te_rm_in += record_syn_rm_te[iv[0]:iv[1]]
record_syn_rm_te_rm_in.annotations['molecule_type'] = 'DNA'
record_syn_rm_te_rm_in.annotations['topology'] = 'linear'
if not args.clean:  
    SeqIO.write(record_syn_rm_te_rm_in, 'syn/' + chromosome + arm + '.with_syn_PCRmark.remove_TE.remove_intergenic.gb', "genbank")
cds_dict = {}
for fea in record_syn_rm_te_rm_in.features:
    if fea.type == 'CDS':
        gene = fea.qualifiers['label'][0].split('.')[0]
        if not gene in cds_dict:
            cds_dict[gene] = [fea.location.strand]
        cds_dict[gene].append([int(fea.location.start), int(fea.location.end)]) 
        s_seq = fea.extract(record_syn_rm_te_rm_in.seq)[-3:] 
        if not str(s_seq).upper() == 'TAA':
            if fea.location.strand == 1:
                s_pos = [int(fea.location.end) - 3, int(fea.location.end)]
                taa = Seq('TAA')  
            else:
                s_pos = [int(fea.location.start), int(fea.location.start) + 3]
                taa = Seq('TTA')
            sf = SeqFeature(SimpleLocation(ExactPosition(s_pos[0]), ExactPosition(s_pos[1]), strand = fea.location.strand), type = 'misc_feature')
            sf.qualifiers['locus_tag'] = str(s_seq).upper() + '>TAA'
            record_syn_rm_te_rm_in.features.append(sf)
            record_syn_rm_te_rm_in.seq = record_syn_rm_te_rm_in.seq[:s_pos[0]] + taa + record_syn_rm_te_rm_in.seq[s_pos[1]:]
record_syn_rm_te_rm_in.annotations['molecule_type'] = 'DNA'
record_syn_rm_te_rm_in.annotations['topology'] = 'linear'
if not args.clean:
    SeqIO.write(record_syn_rm_te_rm_in, 'syn/' + chromosome + arm + '.with_syn_PCRmark.remove_TE.remove_intergenic.stop_codon2TAA.gb', "genbank")

loxp_pos = []
for gene in cds_dict:
    gene_pos_list = []
    if cds_dict[gene][0] == 1:
        for pos in cds_dict[gene][1:]:
            gene_pos_list.append(pos[1] + 3)
        loxp_pos.append(max(gene_pos_list))
    else:
        for pos in cds_dict[gene][1:]:
            gene_pos_list.append(pos[0] - 3)
        loxp_pos.append(min(gene_pos_list))
loxp_pos.sort()
loxpsym = Seq('ATAACTTCGTATAATGTACATTATACGAAGTTAT')
i = 0
record_arm_tem2 = record_syn_rm_te_rm_in
for pos in loxp_pos:
    record_arm_loxp = SeqRecord(record_arm_tem2.seq) 
    lp_pos = pos + 34 * i 
    record_arm_loxp.seq = record_arm_loxp.seq[:lp_pos] + loxpsym + record_arm_loxp.seq[lp_pos:] 
    for fea in record_arm_tem2.features: 
        if fea.location.start > lp_pos:
            sf = SeqFeature(fea.location + 34, type = fea.type)
        elif fea.location.end > lp_pos:
            sf = SeqFeature(SimpleLocation(ExactPosition(fea.location.start), ExactPosition(fea.location.end + 34), strand = fea.location.strand), type = fea.type)
        else:
            sf = SeqFeature(fea.location, type = fea.type)
        sf.qualifiers = fea.qualifiers 
        record_arm_loxp.features.append(sf) 
    sf = SeqFeature(SimpleLocation(ExactPosition(lp_pos), ExactPosition(lp_pos + 34), strand = 1), type = "protein_bind")
    sf.qualifiers['label'] = "LoxPsym"
    record_arm_loxp.features.append(sf)
    record_arm_tem2 = record_arm_loxp
    i += 1
record_arm_loxp.annotations['molecule_type'] = 'DNA'
record_arm_loxp.annotations['topology'] = 'linear'
record_arm_loxp.id = 'synMoss.' + chromosome + arm
SeqIO.write(record_arm_loxp, 'syn/synMoss.' + chromosome + arm + '.gb', "genbank")



