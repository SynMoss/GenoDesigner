package com.hmzhkj.gene.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Location {
    private Integer start;
    private Integer end;
    private Integer strand;
    public Location(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }
    public Location() {
    }
}
