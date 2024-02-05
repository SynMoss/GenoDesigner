package com.hmzhkj.gene.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

 @Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddProjectMembersDto {
    String projectId;
    String[] staffIds;
}
