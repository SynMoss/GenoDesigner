package com.hmzhkj.gene.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ZipUtil;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.gene.mapper.ProgrammeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class BioinformaticsPipelineService {

    private final ProgrammeMapper programmeMapper;
    private final String separator = File.separator;


    private String getPrefixPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String prefixPath = "";
        if (osName.contains("win")) {
            prefixPath = "D:" + separator + "gene" + separator;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            prefixPath = separator + "opt" + separator + "big-folder" + separator + "BioinformaticsPipeline" + separator;
        } else if (osName.contains("mac")) {
            prefixPath = separator + "usr" + separator + "local" + separator;
        } else {
            throw new ServiceException("Unknown operating system");
        }
        return prefixPath + "result" + separator;
    }




    public ResponseEntity<?> geneExpression(Map<String, String> params) {
        String prefixPath = getPrefixPath();

        String geneid = params.get("geneid");
        String snowId = IdUtil.getSnowflakeNextIdStr();

        String basePath = prefixPath + snowId;

        FileUtil.mkdir(basePath);
        File configYaml = FileUtil.file(basePath + separator + "config.yaml");
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(configYaml);
            printWriter.println("geneid: \""+geneid+"\"");
        }catch (FileNotFoundException e){
            log.error(e.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }

        runCommand(basePath,"geneExpression");

        File zip = ZipUtil.zip(basePath+separator+"output",basePath+".zip");
        FileSystemResource fileSystemResource = new FileSystemResource(zip);
        ResponseEntity<InputStreamResource> response = null;
        try {
            response = ResponseEntity
                    .ok()
                    .contentLength(fileSystemResource.contentLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(fileSystemResource.getInputStream()));
        } catch (IOException e) {
            throw new ServiceException("Download failed!");
        }finally {
            FileUtil.del(basePath);
            FileUtil.del(zip.getPath());
        }

        return response;
    }

    public Map<String,Object> blast2(Map<String, String> params) {
        String prefixPath = getPrefixPath();
        String chr = params.get("chr");
        String start = params.get("start");
        String end = params.get("end");
        String seq = params.get("seq");
        String number = params.get("number");
        String type = null;
        if("M0000002".equals(number)){
            type = "blast2go_source";
        }else if("M0000004".equals(number)){
            type = "blast2kegg_source";
        }else{
            throw new ServiceException("The current analysis program does not exist!");
        }
        String snowId = IdUtil.getSnowflakeNextIdStr();
        String basePath = prefixPath + snowId;
  
        FileUtil.mkdir(basePath);
        File configYaml = FileUtil.file(basePath + separator + "config.yaml");
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(configYaml);
            printWriter.println("chr: \""+chr+"\"");
            printWriter.println("start: "+start);
            printWriter.println("end: "+end);
            printWriter.println("seq: \""+seq+"\"");
            printWriter.println("idmapping: \"/opt/big-folder/BioinformaticsPipeline/DB/duckDB/idmapping.duckdb\"");
            printWriter.println("db: \"/opt/big-folder/BioinformaticsPipeline/DB/swissprot\"");
        }catch (FileNotFoundException e){
            log.error(e.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
        runCommand(basePath,type);

        File csv;
        if(basePath.contains("opt")){
            csv = new File(basePath + separator + "output" + separator + "annotated.csv");

        }else{
            csv = new File("D:\\gene\\annotated.csv");
        }


        if(csv.exists()){

            BufferedReader br = null;
            try {
                String line ;
                StringBuffer sb = new StringBuffer();
                br = new BufferedReader(new FileReader(csv));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                Map<String,Object> map = new HashMap<>();
                map.put("name", sb);
                map.put("start", start);
                map.put("end", end);
                map.put("chr", chr);
                System.err.println(map);
                return map;
            } catch (IOException e) {
                log.error(e.getMessage());
            }finally {
                if(br!=null){
                    try {
                        br.close();
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
                FileUtil.del(basePath );
            }
        }else{
            throw new ServiceException("No annotation information");
        }

        return null;
    }




    private void runCommand(String path,String type) {
        File runPath = new File(path+separator+"run.sh");
        if(path.contains("opt")){
            FileUtil.copy(new File("/opt/big-folder/BioinformaticsPipeline/run.sh"), runPath, true);
        }else{
            FileUtil.copy(new File("D:\\gene\\run.sh"), runPath, true);
        }


        FileWriter  fw = null;
        try {
            fw = new FileWriter(runPath,true);
            fw.write("\n");
            fw.write("cd /opt/big-folder/BioinformaticsPipeline/"+type+"\n");
            fw.write("snakemake --cores 8 --directory "+path+"\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                if(fw!=null){
                    fw.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        System.err.println(path);
        if(path.contains("opt")){
            String[]command= new String[]{"sh",runPath.getPath()};
            execCmdOrder(command);
        }
    }

    private void execCmdOrder(String[]command) {
        System.out.println("Executing commands...");
        if (StringUtils.isEmpty(command)){
            throw new RuntimeException("The command that needs to be executed cannot be empty");
        }

        BufferedReader br=null;
        Process process=null;
        try {

            System.err.println(Arrays.toString(command));
            process = Runtime.getRuntime().exec(command);


            br=new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            String tmp;

            while ((tmp= br.readLine())!=null){
                log.info(tmp);
            }

            while (process.isAlive()) {
                process.waitFor(10L, TimeUnit.MINUTES);
            }
        } catch (InterruptedException | IOException e) {
            log.error(e.getMessage());
        }finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (process != null) {
                process.destroy();
            }
            System.out.println("completion of enforcement!");
        }
    }


}
