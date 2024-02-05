package com.hmzhkj.file.service;

import com.hmzhkj.common.core.web.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

 
public interface ISysFileService
{
     
    String uploadFile(MultipartFile file) throws Exception;

     
    AjaxResult uploadBigFile(MultipartFile file, String identifier, String chunkNumber);

    String mergeBigFile(String identifier,String filename,String totalSize,String folderPath) throws IOException;
}
