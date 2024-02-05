package com.hmzhkj.gene.domain;

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
public class CreateWithTemplateDto {
    private String programmeName;
    private String projectId;
    private String pack_file_id;
}
