package com.hmzhkj.gene.constant;

 
public enum PyFeatureEnum {
    DELETE_FEATURE("deleteFeature"),
    REDUCE_GENOME("reduceGenome"),
    GLOBAL_INSERTION("globalInsertion"),
    IMPORT_FEATURE("importFeature"),
    CODON_REPLACE("codonReplace"),
    MERGE_FEATURE("mergeFeature");
    private String value;
    PyFeatureEnum(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
