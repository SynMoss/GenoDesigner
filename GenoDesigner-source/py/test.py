import pandas as pd
from Bio import SeqIO
from Bio.Seq import Seq,MutableSeq
from Bio.SeqRecord import SeqRecord
from Bio.SeqFeature import SeqFeature, ExactPosition, SimpleLocation, CompoundLocation
from GenoDesigner import merge_intervals, remove_intersection, iv_inter, interval_intersection, feature_cut, feature_link
import multiprocessing
import os
    


# cutGbFile("F:\\download\\gb","F:\\download\\gb\\test.gb")
# with open("D:\\upload\\sequence\\1739858406538989569\\1704325054.594271_CP000716.gb") as input_handle:
#         print(input_handle.read())
if __name__ == "__main__":
    if True:
        a = 1
    else:
        a=2
    print(a)
    # print(os.path.dirname('F:\\download\\gb\\test.gb'))
    # with open("F:\\download\\gb\\1687824201.1979003_NC_001133.gb") as input_handle:
    #     str = input_handle.read()
    #     mutable_seq = MutableSeq(str)
    #     mutable_seq.reverse_complement
    #     mutable_seq.insert(1,'aaaa')
    #     print(mutable_seq)