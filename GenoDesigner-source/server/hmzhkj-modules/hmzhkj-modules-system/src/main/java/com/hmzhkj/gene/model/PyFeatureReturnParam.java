package com.hmzhkj.gene.model;

import com.hmzhkj.gene.domain.Feature;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
public class PyFeatureReturnParam {
    private String excelPath;
    private List<Feature> features;
}
