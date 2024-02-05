package com.hmzhkj.common.core.enums;

public enum RegisterTypeEnum {
    EMAIL("0"),
    PHONE("1");
    private String type;
    RegisterTypeEnum(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

}
