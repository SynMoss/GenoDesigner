package com.hmzhkj.gene.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Range {
    private Integer start;
    private Integer end;
    private String type;
    public Range(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }
    public Range(Location location) {
        this.start = location.getStart();
        this.end = location.getEnd();
    }
    public Range(Integer start, Integer end,String type) {
        this.start = start;
        this.end = end;
        this.type = type;
    }
    public Range() {
    }
}
