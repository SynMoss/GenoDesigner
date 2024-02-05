package com.hmzhkj.gene.constant;

 
public enum ImportConfigEnum {
    CHR("CHR"),
    START("START"),
    END("END"),
    EDIT_TYPE("EDIT_TYPE"),
    SEQ("SEQ"),

    FEATURE_NAME("FEATURE_NAME"),
    FEATURE_TYPE("FEATURE_TYPE"),
         STRAND("STRAND"),
         ORI_SEQ("ORI_SEQ");
    private String value;
    ImportConfigEnum(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
