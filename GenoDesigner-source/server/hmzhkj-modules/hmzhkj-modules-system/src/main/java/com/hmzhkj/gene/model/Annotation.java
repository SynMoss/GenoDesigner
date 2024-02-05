package com.hmzhkj.gene.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
public class Annotation {
    private String key;
    private String sourceFileName;
    private String source;
    private Integer isValid;
    private String keyWords;
    public Annotation(String key) {
        this.key = key;
    }

    public Annotation() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Annotation that = (Annotation) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
