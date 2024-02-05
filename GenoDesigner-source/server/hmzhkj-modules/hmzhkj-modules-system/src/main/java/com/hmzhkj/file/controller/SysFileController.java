package com.hmzhkj.file.controller;

import cn.hutool.core.io.file.FileWriter;
import com.hmzhkj.common.core.utils.uuid.IdUtils;
import com.hmzhkj.common.core.web.domain.AjaxResult;
import com.hmzhkj.file.model.MergeParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.hmzhkj.common.core.domain.R;
import com.hmzhkj.common.core.utils.file.FileUtils;
import com.hmzhkj.file.service.ISysFileService;
import com.hmzhkj.system.domain.SysFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class SysFileController
{
    @Value("${file.prefix}")
    public String localFilePrefix;


    @Value("${file.domain}")
    public String domain;


    @Value("${file.path}")
    private String localFilePath;
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Autowired
    private ISysFileService sysFileService;


    @PostMapping("upload")
    public R<SysFile> upload(MultipartFile file)
    {
        try
        {

            String url = sysFileService.uploadFile(file);
            SysFile sysFile = new SysFile();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setRealPath(url.replace(domain + localFilePrefix,localFilePath));
            sysFile.setUrl(url);
            return R.ok(sysFile);
        }
        catch (Exception e)
        {
            log.error("Failed to upload file", e);
            return R.fail(e.getMessage());
        }
    }
    @PostMapping("/export/json")
    public ResponseEntity<?> exportDesignJson(@RequestBody String jsonString) throws IOException {
        String filePath = localFilePath+ File.separator+ "export_json" + File.separator+IdUtils.simpleUUID()+".txt";
        FileWriter fwTask = new FileWriter(filePath);
        fwTask.write(jsonString);
        FileSystemResource fileSystemResource = new FileSystemResource(fwTask.getFile());
        return ResponseEntity
                .ok()
                .contentLength(fileSystemResource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(fileSystemResource.getInputStream()));
    }
    @ApiOperation(value = "Merge large files")
    @PostMapping(value = "/mergeBigFiles")
    public AjaxResult mergeBigFiles(@RequestBody List<MergeParam> list) throws IOException {
        List<String> result = new ArrayList<>();
        for (MergeParam mergeParam : list) {
            result.add(sysFileService.mergeBigFile(mergeParam.getIdentifier(),mergeParam.getFilename(),
                    mergeParam.getTotalSize(),
                    mergeParam.getFolderPath()));
        }
        return AjaxResult.success("Merge successful",result);
    }

    @ApiOperation(value = "Upload a large file of the number package")
    @PostMapping(value = "/uploadBigFile")
    public AjaxResult mergeBigFile(MultipartFile file, String identifier, String chunkNumber){
        return sysFileService.uploadBigFile(file, identifier,chunkNumber);
    }
}