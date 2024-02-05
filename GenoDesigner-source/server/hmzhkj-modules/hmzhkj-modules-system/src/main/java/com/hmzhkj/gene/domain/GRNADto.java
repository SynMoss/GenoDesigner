package com.hmzhkj.gene.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GRNADto {
    private String programmeId;
    private Integer historyStep;
    private MultipartFile configureFile;


     
    private String gRNA_type;
    private String gene_upstream_len;
    private String gene_downstream_len;
    private String enzyme_name;
    private String enzyme_site;

    private String forward_overhang;
    private String reverse_overhang;
    private String design_type;
    private String model;
    private String score_gc;

    private String scoringMethod;
    private String target_type;
    private String guid_size;
    private String pam;
    private String max_oftargets;

    private String max_mismatches;
    private String grna_num;
    private String tale_min;
    private String tale_max;
}
