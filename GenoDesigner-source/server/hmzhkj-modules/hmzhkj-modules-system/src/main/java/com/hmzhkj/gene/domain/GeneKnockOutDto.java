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
public class GeneKnockOutDto {
    private String programmeId;
    private Integer historyStep;
    private MultipartFile vector;
    private MultipartFile kanMX;
    private MultipartFile configureFile;
    private String left_flank;
    private String right_flank;
    private String extend_size;
    private String len_overhang;

    private String max_num;
    private String min_pt;
    private String max_pt;
    private String opt_pt;
    private String min_gc;
    private String max_gc;
    private String opt_gc;
    private String min_tm;
    private String max_tm;
    private String opt_tm;
    private String diff_tm;
    private String max_poly;
    private String left_start;
    private String right_start;
    private String left_pt;
    private String right_pt;
    private String window;
}
