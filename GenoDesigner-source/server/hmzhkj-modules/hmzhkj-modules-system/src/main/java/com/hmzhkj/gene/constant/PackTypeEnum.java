package com.hmzhkj.gene.constant;

 
public enum PackTypeEnum {
    FASTA("0"),
    GENBANK("1"),
    HISTORY("2");
    private String value;
    PackTypeEnum(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
