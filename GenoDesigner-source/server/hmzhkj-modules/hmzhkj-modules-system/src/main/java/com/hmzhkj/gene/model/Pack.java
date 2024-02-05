package com.hmzhkj.gene.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Pack {
    private List<String> programmeIdList;
    private List<String> packTypeList;
}
