package com.hmzhkj.file.service;

import com.hmzhkj.common.core.utils.FileUploadUtils;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.List;


@Primary
@Service
public class LocalSysFileServiceImpl implements ISysFileService
{

    @Value("${file.prefix}")
    public String localFilePrefix;


    @Value("${file.domain}")
    public String domain;
    

    @Value("${file.path}")
    private String localFilePath;
    @Value("${path.sequence}")
    private String sequencePath;
    @Override
    public AjaxResult uploadBigFile(MultipartFile file, String identifier, String chunkNumber) {
        String path= localFilePath + File.separator + identifier;
        String resultPath = FileUploadUtils.uploadMultipartFile(file, path, chunkNumber);
        return AjaxResult.success("Upload successful", resultPath);
    }

    @Override
    public String mergeBigFile(String identifier,String filename,String totalSize,String folderPath) throws IOException {
        String identifierPath = localFilePath + File.separator + identifier;
        String path;
        if(StringUtils.isNotEmpty(folderPath)){
            path = sequencePath+File.separator +folderPath+File.separator+ totalSize+"-"+filename;
            File targetFolder = new File(sequencePath+File.separator +folderPath);
            if(!targetFolder.exists()){
                targetFolder.mkdirs();
            }
        }else{
            path = localFilePath+File.separator + totalSize+"-"+filename;
        }
        File newFile = new File(path);
        if(newFile.exists()){
            newFile.delete();
        }
        File file = new File(identifierPath);
        File[] files = file.listFiles();

        List<File> fileList = Arrays.asList(files);
        fileList.sort((file1,file2)->{
            int a = Integer.valueOf(file1.getName());
            int b = Integer.valueOf(file2.getName());
            return a- b;
        });
        FileInputStream inputStream;
        byte[] byt = new byte[5 * 1024 * 1024];
        int len;
        FileOutputStream outputStream = new FileOutputStream(newFile,  true);
        for (File f: fileList){
            inputStream = new FileInputStream(f);
            while ((len = inputStream.read(byt))!=-1) {
                outputStream.write(byt, 0, len);
            }
            inputStream.close();
        }
        for (File f: files){
            f.delete();
        }
        file.delete();
        outputStream.close();

        return newFile.getName();
    }



    @Override
    public String uploadFile(MultipartFile file) throws Exception
    {
        String name = FileUploadUtils.upload(localFilePath, file);
        String url = domain + localFilePrefix + name;
        return url;
    }
}
