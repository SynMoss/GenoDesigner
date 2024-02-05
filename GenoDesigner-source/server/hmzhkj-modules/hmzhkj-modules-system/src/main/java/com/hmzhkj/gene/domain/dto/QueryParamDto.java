package com.hmzhkj.gene.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class QueryParamDto {
    private String code;
    private String name;
    private String createStaffNo;
    private List<String> codes;
 }
