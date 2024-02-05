package com.hmzhkj.gene.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FeatureRepeatCondition {
    private Long sequenceId;
    private String groupByName;
    private String groupByStart;
    private String groupByEnd;
    private String groupBySource;
    private String groupByType;
}
