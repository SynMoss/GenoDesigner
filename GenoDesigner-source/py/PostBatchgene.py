#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import sys
from Bio.SeqIO import parse as seqio_parse
from argparse import ArgumentParser
import pdb
from flask import Flask, request

'''
by yang jinxurong
20230106
USAGE: python bin/BatchGene.py -l inputs/Insert_Delet.config.txt -i inputs/GCF_000146045.2_R64_genomic.gbff -o outputs
'''

def isValue(element):
    return (element is not None and element != '-')

class BatchGene:
    def __init__(self, configFile, gbFile, outdir):
        self.config = configFile
        self.gbFile = gbFile
        self.outdir = outdir
        self.editList = {}
        self.geneDic = {} # store gene and its locus info
        self.seqDict = {} # store chr sequence to generate sequence before editing

    def getEditList(self):
        def getLinebyType(linelist):
            chr = linelist[0] #chr
            ID = linelist[1] # id
            start = linelist[2] # start 
            end = linelist[3] # end
            type = linelist[4] # edit type
            seq = linelist[5] # seq
            subSeq=""

            if type == "INSERT":
                end = start # change according to software requirements
                subSeq = seq
                preSeq = "-"
                if not isValue(start):
                    print("start position not valit, exit ...")
                    os._exit(1)
            else:
                if isValue(ID):
                    locus = [value for key,value in self.geneDic[ID].items()]
                    chr = locus[0]
                    start = locus[1]
                    end = locus[2]
                preSeq = self.seqDict[chr][(int(start)-1):int(end)]  # fix me: seq from gb file

                if type == "DELETE":
                    subSeq = "-"
                    type = "CUT"
                elif type == "REPLACE":
                    subSeq = seq
                else:
                    print(type)
                    print("type value not valid,exit ...")
            return ([chr, ID, start, end, type, subSeq, preSeq])

        with open(self.config, "r") as fin:
            i = 0
            for line in fin.readlines():
                if line.startswith("ChrID"):
                    continue
                linelist = line.strip().split("\t")
                if len(linelist) < 6:
                    print("Format invalid")
                    os._exit(1)
                self.editList[i] = getLinebyType(linelist)
                print(i)
                i += 1

    def getGeneDic(self):
        records = [rec for rec in seqio_parse(self.gbFile, "genbank")]
        
        for record in records:
            self.seqDict[record.name] = str(record.seq)

        def getLine(record, feature):
            if feature.qualifiers.get('locus_tag') is not None:
                gene = feature.qualifiers.get('locus_tag')[0]
            elif feature.qualifiers.get('gene') is not None:
                gene = feature.qualifiers.get('gene')[0]
            elif feature.qualifiers.get('label') is not None:
                gene = feature.qualifiers.get('label')[0]
            else:
                raise Exception("GenBank keys not specific. Please provide one of the following for each feature: 'locus_tag'，'gene'，'label' ! ")
            lineList = [record.name, gene, str(feature.location.start.position), str(feature.location.end.position)]
            return(lineList)

        def getGeneDict(recs, Type="gene"):
            # Type in ["gene", "mRNA", "CDS"]
            geneDic = {}
            for record in recs:
                for feature in record.features:
                    if feature.type == Type:
                        lineList = getLine(record, feature)
                        if lineList[1] in geneDic:
                            print(lineList)
                            geneDic[lineList[1]]
                            continue
                        else:
                            geneDic[lineList[1]] = {"chrID": lineList[0], 
                                                    "Start": lineList[2],
                                                    "end": lineList[3]}
            return(geneDic)

        def getGenePos2tsv(recs, Type="gene"):
            # used when getting geneDict cost too much RAM
            # files split by chrID
            for record in recs:
                fout=open(record.name + ".tsv", "w")
                fout.write("chrID\tID\tStart\tEnd\n")
                for feature in record.features:
                    if feature.type == Type:
                        fout.write("\t".join(getLine(record, feature))+"\n")
                fout.close()

        self.geneDic = getGeneDict(records, Type="gene")

    def splitEditList(self):
        chrList = []
        # pdb.set_trace()
        fout = open(os.path.join(self.outdir), "w")
        for key, value in self.editList.items():
            fout.write("\t".join(value)+"\n")
        fout.close()

app = Flask(__name__)

# fix me: delete
def allowed_file(filename, extensionSet):
	return '.' in filename and filename.rsplit('.', 1)[1].lower() in extensionSet

ALLOWED_gb_EXTENSIONS = set(['gbff', 'gb'])
ALLOWED_config_EXTENSIONS = set(['txt', 'tsv'])


def batchrun(configFile, gbFile, outdir):
    bg = BatchGene(configFile, gbFile, outdir)
    bg.getGeneDic()
    bg.getEditList()
    bg.splitEditList()

# @app.route('/faGffToGb', methods=['POST'])
# def add():
# 	configFile = request.get_json()['config']
# 	if not allowed_file(configFile, ALLOWED_config_EXTENSIONS):
# 		print("file format not valid, exit...")
# 		return (configFile, 200)
		
# 	gbFile = request.get_json()['gb']
# 	if not allowed_file(gbFile, ALLOWED_gb_EXTENSIONS):
# 		print("file format not valid, exit...")
# 		return (gbFile, 200)

# 	outdir = request.get_json()['outdir']
# 	# call convertor
# 	batchrun(configFile, 
#              gbFile, 
#              outdir)
# 	return (outdir, 200)

if __name__ == "__main__":
    app.run(host='0.0.0.0')