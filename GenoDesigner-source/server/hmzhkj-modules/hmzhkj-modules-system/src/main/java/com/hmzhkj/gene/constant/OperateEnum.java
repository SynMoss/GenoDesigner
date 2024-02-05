package com.hmzhkj.gene.constant;

 
public enum OperateEnum {
    INSERT("INSERT"),
    REPLACE("REPLACE"),
    CUT("CUT"),
    FEATURE_INSERT("FEATURE_INSERT"),
    FEATURE_INSERT_LIST("FEATURE_INSERT_LIST"),

    FEATURE_CUT("FEATURE_CUT"),
    FEATURE_CUT_LIST("FEATURE_CUT_LIST"),
    FEATURE_UPDATE("FEATURE_UPDATE"),
    FEATURE_MERGE("FEATURE_MERGE"),
         FEATURE_CUT_BY_TYPE("DELETE_BY_TYPE"),
         FEATURE_CUT_REDUCTION("TRUNCATE"),
         FEATURE_INSERT_GLOBAL("INSERT_BY_TYPE"),
         CODON_REPLACE("CODON_REPLACE"),
         IMPORT_CONFIG("IMPORT_EDITING");
    private String value;
    OperateEnum(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
