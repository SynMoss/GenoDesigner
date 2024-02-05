package com.hmzhkj.gene.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

 @Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectVo {
    private String id;
    private String projectName;
    private String createStaffName;
}
