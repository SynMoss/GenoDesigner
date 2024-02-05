package com.hmzhkj.gene.model;

import com.hmzhkj.gene.domain.Sequence;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ParsedOptions {
    private Boolean isProtein;
    private String sequenceTypeFromLocus;
    private Boolean lastLineWasFeaturesTag;
    private Integer featureLocationIndentation;
    private int featurePadLength;
    private Boolean lastLineWasLocation;
    private Boolean inclusive1BasedStart;
    private Boolean inclusive1BasedEnd;
    private Sequence sequence;
    private List<String> currentNote;
    private Boolean isEnd;
}
