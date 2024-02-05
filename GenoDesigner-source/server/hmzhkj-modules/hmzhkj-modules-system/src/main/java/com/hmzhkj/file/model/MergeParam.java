package com.hmzhkj.file.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MergeParam {
    private String identifier;
    private String filename;
    private String totalSize;
    private String folderPath;
}
