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
public class PrimerPredictionFromGenBankDto {
    private String programmeId;
    private Integer historyStep;
    private MultipartFile vector;
    private MultipartFile configureFile;

    private String insert_site;
    private String width;
    private String overlap;
    private String arm;
    private String left;
    private String right;

    private String varmr;
    private String varml;
    private String min_primer;

    private String max_primer;
    private String opt_primer;
    private String diff_tm;


}
