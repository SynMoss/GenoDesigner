#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""Convert a GFF and associated FASTA file into GenBank format.

Usage:
    gff_to_genbank.py <GFF annotation file> [<FASTA sequence file> <molecule type>]
    FASTA sequence file: input sequences matching records in GFF. Optional if sequences
    are in the GFF
# python Convert2GB.py -f inputs/XXX.fna -i inputs/XXX.gtf -o outputs/XXX.gb
'''
 molecule type: type of molecule in the GFF file. Defaults to DNA, the most common case.
"""
from __future__ import print_function

from sys import argv
from os import _exit
from Bio.SeqIO import to_dict as seqio_to_dict
from Bio.SeqIO import parse as seqio_parse
from Bio.SeqIO import write as seqio_write
from BCBio.GFF import parse as GFFparse
from Bio import SeqFeature

from flask import Flask, request

INPUT_FEATURE_MAP = {"long_terminal_repeat": "repeat_region",
                     "pseudogene": "gene",
                     "mobile_genetic_element": "mobile_element",
                     "transcript": "mRNA",
                     "repeat region": "repeat_unit",
                     "origin_of_replication": "rep_origin",
                     "regulatory_region":"regulatory",
                     "sequence_feature": "source",
                     "region": "source",
                     "transposable_element":"transposable_element_gene"}

INPUT_IGNORED_FEATURE_LIST = ["start_codon", "stop_codon"]

class Convert2GB:
    def __init__(self, faFile, gxfFile, outFile):
        self.faFile = faFile
        self.gxfFile = gxfFile
        self.gbFile = outFile

    def convertor(self):
        molecule_type="DNA"
        def _fix_ncbi_id(fasta_iter):
            """GenBank identifiers can only be 16 characters; try to shorten NCBI.
            """
            for rec in fasta_iter:
                if len(rec.name) > 16 and rec.name.find("|") > 0:
                    new_id = [x for x in rec.name.split("|") if x][-1]
                    print("Warning: shortening NCBI name %s to %s" % (rec.id, new_id))
                    rec.id = new_id
                    rec.name = new_id
                yield rec
                        

        def _check_gff(gff_iterator, molecule_type):
            """Check GFF files before feeding to SeqIO to be sure they have sequences.
            """
            for rec in gff_iterator:
                if "molecule_type" not in rec.annotations:
                    rec.annotations["molecule_type"] = molecule_type
                yield _flatten_features(rec)


        def _flatten_features(rec):
            """Make sub_features in an input rec flat for output.
            GenBank does not handle nested features, so we want to make
            everything top level.
            """
            out = []
            for f in rec.features:
                cur = [f]
                while len(cur) > 0:
                    nextf = []
                    for curf in cur:
                        if curf.type in INPUT_IGNORED_FEATURE_LIST:
                            continue
                        # start SO mapping
                        if curf.type in INPUT_FEATURE_MAP:
                            curf.type = INPUT_FEATURE_MAP[curf.type]
                        # end SO mapping
                        # fix 20230404
                        if curf.type == "mRNA" and curf.qualifiers.get("gbkey") != None:
                            curf.type = curf.qualifiers.get("gbkey")[0]
                        # end fix
                        out.append(curf)
                        if len(curf.sub_features) > 0:
                            nextf.extend(curf.sub_features)
                    cur = nextf
            rec.features = out
            return rec

        if self.faFile:
            fasta_input = seqio_to_dict(seqio_parse(self.faFile, "fasta"))
        else:
            fasta_input = {}
        gff_iter = GFFparse(self.gxfFile, fasta_input)
        seqio_write(_check_gff(_fix_ncbi_id(gff_iter), molecule_type), self.gbFile, "genbank")
app = Flask(__name__)

def allowed_file(filename, extensionSet):
	return '.' in filename and filename.rsplit('.', 1)[1].lower() in extensionSet

ALLOWED_gf_EXTENSIONS = set(['gff', 'gtf', 'gff3', 'GFF','GTF', 'GFF3'])
ALLOWED_fa_EXTENSIONS = set(['fa', 'fna', 'fasta'])

def con2gb(fa_file, gxf_file, gb_file):
	conv = Convert2GB(fa_file, gxf_file, gb_file)
	conv.convertor()
def test(a):
     return a+1

if __name__ == "__main__":
    app.run(host='0.0.0.0')