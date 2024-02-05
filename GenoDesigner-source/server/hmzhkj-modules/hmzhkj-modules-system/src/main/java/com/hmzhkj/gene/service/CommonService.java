package com.hmzhkj.gene.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonService {
    @Value("${path.sequence}")
    private String sequencePath;
    public String getSequenceFolderPath(String programmeId){
        return sequencePath +"/"+programmeId+"/";
    }
}
