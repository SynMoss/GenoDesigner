package com.hmzhkj.gene.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.gene.mapper.ProgrammeMapper;
import com.hmzhkj.gene.domain.Programme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class AssociatedMoleculeService {

    private final ProgrammeMapper programmeMapper;
    private final AsyncMoleculeService asyncMoleculeService;
    @Value("${path.sequence}")
    private String sequencePath;

    private final String separator = File.separator;


    protected String getPrefixPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String prefixPath = "";
        if (osName.contains("win")) {
            prefixPath = "D:" + separator;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            prefixPath = separator + "root" + separator + ".wine" + separator + "drive_c" + separator + "windows" + separator + "system32" + separator + "molecular" + separator;
        } else if (osName.contains("mac")) {
            prefixPath = separator + "usr" + separator + "local" + separator;
        } else {
            throw new ServiceException("Unknown operating system");
        }
        return prefixPath + "result" + separator;
    }


    public String gRNA(MultipartFile configure, MultipartFile vector, MultipartFile genome, String params) {

        TypeReference<LinkedHashMap<String, String>> typeRef = new TypeReference<LinkedHashMap<String, String>>() {
        };
        Map<String, String> map = JSONObject.parseObject(params, typeRef, Feature.OrderedField);

        Map<String, String> gRNA = JSONObject.parseObject(map.get("gRNA"), new TypeReference<LinkedHashMap<String, String>>() {
        }, Feature.OrderedField);
        Map<String, String> Primer = JSONObject.parseObject(map.get("Primer"), new TypeReference<LinkedHashMap<String, String>>() {
        }, Feature.OrderedField);

        String programmeId = map.get("programmeId");
        String step = map.get("step");
        String prefixPath = getPrefixPath();
        String basePath = prefixPath + programmeId + separator + step + separator + "gRNA" + separator;
        ;
        String dataPath = "data" + separator;
        String resultPath = "result" + separator;
        String configPath = "config" + separator;

        FileUtil.mkdir(basePath + dataPath);
        FileUtil.mkdir(basePath + resultPath);
        FileUtil.mkdir(basePath + configPath);


        Programme one = programmeMapper.selectById(programmeId);
        Map<String, String> runningState = one.getRunningState();
        if(runningState == null){
            runningState = new HashMap<>();
        }
        runningState.put(step, "0");
        one.setRunningState(runningState);
        programmeMapper.updateById(one);


        File genomeFile = null;
        File configureFile = new File(basePath + dataPath + configure.getOriginalFilename());
        File vectorFile = new File(basePath + dataPath + vector.getOriginalFilename());

        if (genome != null) {
            genomeFile = new File(basePath + dataPath + genome.getOriginalFilename());
            try {
                genome.transferTo(genomeFile);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        } else {
            Programme programme = programmeMapper.selectById(programmeId);
            File sourceFile = new File(sequencePath + separator + programmeId + separator + programme.getSourceFileMergeName());
            genomeFile = new File(prefixPath + programmeId + separator + programme.getSourceFileMergeName());
            if (!genomeFile.exists()) {
                FileUtil.copy(sourceFile, genomeFile, true);
            }
        }

        try {
            configure.transferTo(configureFile);
            vector.transferTo(vectorFile);
        } catch (IOException e) {
            log.error(e.getMessage());
        }


        File gRNAConf = FileUtil.file(basePath + configPath + "gRNA.conf");
        File primerConf = FileUtil.file(basePath + configPath + "Primer.conf");
        String winePath = null;
        if(prefixPath.contains(".wine")){
            winePath = basePath.replace(prefixPath, "C:\\windows\\system32\\molecular\\result\\");
        }else {
            winePath = basePath;
        }
        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(gRNAConf);
            printWriter.println("[P1]");
            printWriter.println(("outdir=" + winePath + resultPath + "gRNA_result").replace("/", "\\"));
            if(prefixPath.contains(".wine")){
                printWriter.println("genome=" + genomeFile.getPath().replace(prefixPath, "C:\\windows\\system32\\molecular\\result\\"));
            }else {
                printWriter.println("genome=" + genomeFile.getPath());
            }
            printWriter.println(("gene_conf=" + winePath + dataPath + configure.getOriginalFilename()).replace("/", "\\"));
            printWriter.println("[P2]");

            for (Map.Entry<String, String> entry : gRNA.entrySet()) {
                printWriter.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }


        try {
            printWriter = new PrintWriter(primerConf);
            printWriter.println("[P1]");
            printWriter.println(("outdir=" + winePath + resultPath).replace("/", "\\"));
            printWriter.println(("vector=" + winePath + dataPath + vector.getOriginalFilename()).replace("/", "\\"));
            if(prefixPath.contains(".wine")){
                printWriter.println("genome=" + genomeFile.getPath().replace(prefixPath, "C:\\windows\\system32\\molecular\\result\\"));
            }else {
                printWriter.println("genome=" + genomeFile.getPath());
            }
            printWriter.println(("knockout_conf=" + winePath + dataPath + configure.getOriginalFilename()).replace("/", "\\"));
            for (Map.Entry<String, String> entry : Primer.entrySet()) {
                if ("max_num".equals(entry.getKey())) {
                    printWriter.println("[P2]");
                }
                printWriter.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }

        String out = asyncMoleculeService.asyncGRNA(programmeId, step, prefixPath, basePath, resultPath, gRNAConf, primerConf);
        return out;
    }






    @Transactional
    public String primerPrediction(MultipartFile configure, MultipartFile vector, MultipartFile genome, String params) {

        TypeReference<LinkedHashMap<String, String>> typeRef = new TypeReference<LinkedHashMap<String, String>>() {
        };
        Map<String, String> map = JSONObject.parseObject(params, typeRef, Feature.OrderedField);
        System.err.println(map.get("input"));
        Map<String, String> input = JSONObject.parseObject(map.get("input"), new TypeReference<LinkedHashMap<String, String>>() {
        }, Feature.OrderedField);

        String programmeId = map.get("programmeId");
        String step = map.get("step");
        String prefixPath = getPrefixPath();
        String basePath = prefixPath + programmeId + separator + step + separator + "Primer_Prediction" + separator;

        String dataPath = "data" + separator;
        String resultPath = "result" + separator;
        String configPath = "config" + separator;


        FileUtil.mkdir(basePath + dataPath);
        FileUtil.mkdir(basePath + resultPath);
        FileUtil.mkdir(basePath + configPath);


        Programme one = programmeMapper.selectById(programmeId);
        Map<String, String> runningState = one.getRunningState();
        if(runningState == null){
            runningState = new HashMap<>();
        }
        runningState.put(step, "0");
        one.setRunningState(runningState);
        programmeMapper.updateById(one);

        File genomeFile = null;
        File configureFile = new File(basePath + dataPath + configure.getOriginalFilename());
        File vectorFile = new File(basePath + dataPath + vector.getOriginalFilename());

        if (genome != null) {
            genomeFile = new File(basePath + dataPath + genome.getOriginalFilename());
            try {
                genome.transferTo(genomeFile);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        } else {
            Programme programme = programmeMapper.selectById(programmeId);
            File sourceFile = new File(sequencePath + separator + programmeId + separator + programme.getSourceFileMergeName());
            genomeFile = new File(prefixPath + programmeId + separator + programme.getSourceFileMergeName());
            if (!genomeFile.exists()) {
                FileUtil.copy(sourceFile, genomeFile, true);
            }
        }

        try {
            configure.transferTo(configureFile);
            vector.transferTo(vectorFile);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        File inputConf = FileUtil.file(basePath + configPath + "input.conf");
        String winePath = null;
        if(prefixPath.contains(".wine")){
            winePath = basePath.replace(prefixPath, "C:\\windows\\system32\\molecular\\result\\");
        }else {
            winePath = basePath;
        }
        PrintWriter printWriter = null;


        try {
            printWriter = new PrintWriter(inputConf);
            printWriter.println("[P1]");
            if(prefixPath.contains(".wine")){
                printWriter.println("genome=" + genomeFile.getPath().replace(prefixPath, "C:\\windows\\system32\\molecular\\result\\"));
            }else {
                printWriter.println("genome=" + genomeFile.getPath());
            }
            printWriter.println(("vector=" + winePath + dataPath + vector.getOriginalFilename()).replace("/", "\\"));
            printWriter.println(("gene_conf=" + winePath + dataPath + configure.getOriginalFilename()).replace("/", "\\"));
            printWriter.println(("outdir=" + winePath + resultPath).replace("/", "\\"));
            printWriter.println("[P2]");

            for (Map.Entry<String, String> entry : input.entrySet()) {
                printWriter.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }

        String out = asyncMoleculeService.asyncPrimerPrediction(programmeId, step, prefixPath, basePath, resultPath, inputConf);

        return out;
    }





    public String geneKnockout(MultipartFile configure, MultipartFile vector, MultipartFile kanMX, MultipartFile genome, String params) {

        TypeReference<LinkedHashMap<String, String>> typeRef = new TypeReference<LinkedHashMap<String, String>>() {
        };
        Map<String, String> map = JSONObject.parseObject(params, typeRef, Feature.OrderedField);

        Map<String, String> P1 = JSONObject.parseObject(map.get("P1"), new TypeReference<LinkedHashMap<String, String>>() {
        }, Feature.OrderedField);
        Map<String, String> P2 = JSONObject.parseObject(map.get("P2"), new TypeReference<LinkedHashMap<String, String>>() {
        }, Feature.OrderedField);

        String programmeId = map.get("programmeId");
        String step = map.get("step");
        String prefixPath = getPrefixPath();
        String basePath = prefixPath + programmeId + separator + step + separator + "Gene_Knockout" + separator;

        String dataPath = "data" + separator;
        String resultPath = "result" + separator;
        String configPath = "config" + separator;


        FileUtil.mkdir(basePath + dataPath);
        FileUtil.mkdir(basePath + resultPath);
        FileUtil.mkdir(basePath + configPath);


        Programme one = programmeMapper.selectById(programmeId);
        Map<String, String> runningState = one.getRunningState();
        if(runningState == null){
            runningState = new HashMap<>();
        }
        runningState.put(step, "0");
        one.setRunningState(runningState);
        programmeMapper.updateById(one);

        File genomeFile = null;
        File configureFile = new File(basePath + dataPath + configure.getOriginalFilename());
        File vectorFile = new File(basePath + dataPath + vector.getOriginalFilename());
        File kanMXFile = new File(basePath + dataPath + kanMX.getOriginalFilename());

        if (genome != null) {
            genomeFile = new File(basePath + dataPath + genome.getOriginalFilename());
            try {
                genome.transferTo(genomeFile);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        } else {
            Programme programme = programmeMapper.selectById(programmeId);
            File sourceFile = new File(sequencePath + separator + programmeId + separator + programme.getSourceFileMergeName());
            genomeFile = new File(prefixPath + programmeId + separator + programme.getSourceFileMergeName());
            if (!genomeFile.exists()) {
                FileUtil.copy(sourceFile, genomeFile, true);
            }
        }

        try {
            configure.transferTo(configureFile);
            vector.transferTo(vectorFile);
            kanMX.transferTo(kanMXFile);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        File inputConf = FileUtil.file(basePath + configPath + "input.conf");
        String winePath = null;
        if(prefixPath.contains(".wine")){
            winePath = basePath.replace(prefixPath, "C:\\windows\\system32\\molecular\\result\\");
        }else {
            winePath = basePath;
        }
        PrintWriter printWriter = null;


        try {
            printWriter = new PrintWriter(inputConf);
            printWriter.println("[P1]");
            if(prefixPath.contains(".wine")) {
                printWriter.println("primer3_bin=C:\\windows\\system32\\molecular\\Gene_Knockout_Beta_Kan\\bin\\Predicting_Primer.exe");
            }else{
                printWriter.println("primer3_bin=D:\\runBin\\Gene_Knockout_Beta_Kan\\bin\\Predicting_Primer.exe");
            }
            printWriter.println(("outdir=" + winePath + resultPath).replace("/", "\\"));
            if(prefixPath.contains(".wine")){
                printWriter.println("genome=" + genomeFile.getPath().replace(prefixPath, "C:\\windows\\system32\\molecular\\result\\"));
            }else {
                printWriter.println("genome=" + genomeFile.getPath());
            }
            printWriter.println(("vector=" + winePath + dataPath + vector.getOriginalFilename()).replace("/", "\\"));
            printWriter.println(("kanmx=" + winePath + dataPath + kanMX.getOriginalFilename()).replace("/", "\\"));
            printWriter.println(("knockout_conf=" + winePath + dataPath + configure.getOriginalFilename()).replace("/", "\\"));
            for (Map.Entry<String, String> entry : P1.entrySet()) {
                printWriter.println(entry.getKey() + "=" + entry.getValue());
            }

            printWriter.println("[P2]");
            for (Map.Entry<String, String> entry : P2.entrySet()) {
                printWriter.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }

        String out = asyncMoleculeService.asyncGeneKnockout(programmeId, step, prefixPath, basePath, resultPath, inputConf);
        return out;
    }




    public String other(MultipartFile configure, String params) {

        TypeReference<LinkedHashMap<String, String>> typeRef = new TypeReference<LinkedHashMap<String, String>>() {
        };
        Map<String, String> map = JSONObject.parseObject(params, typeRef, Feature.OrderedField);
        String programmeId = map.get("programmeId");
        String step = map.get("step");
        String prefixPath = getPrefixPath();
        String basePath = prefixPath + programmeId + separator + step + separator + "other" + separator;

        String dataPath = "data" + separator;
        String resultPath = "result" + separator;
        String configPath = "config" + separator;

        FileUtil.mkdir(basePath + dataPath);
        FileUtil.mkdir(basePath + resultPath);
        FileUtil.mkdir(basePath + configPath);

        File configureFile = new File(basePath + dataPath + configure.getOriginalFilename());



        try {
            configure.transferTo(configureFile);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        Programme one = programmeMapper.selectById(programmeId);
        Map<String, Map<String,String>> molecularPack = one.getMolecularPack();
        if(molecularPack == null ){
            molecularPack = new HashMap<>();
            molecularPack.put(step,new HashMap<>());
        }else if(molecularPack.get(step)==null){
            molecularPack.put(step,new HashMap<>());
        }
        molecularPack.get(step).put("other", basePath + "data");
        one.setMolecularPack(molecularPack);
        programmeMapper.updateById(one);
        return null;
    }




    public ResponseEntity<?> resultDownload(String programmeId,String step) throws IOException {

        String filePath = getPrefixPath()+programmeId+separator+step+".zip";
        File file = new File(filePath);

        if(!file.exists()){
            Programme programme = programmeMapper.selectById(programmeId);
            Map<String, Map<String, String>> molecularPack = programme.getMolecularPack();
            File dir = new File(getPrefixPath()+programmeId+separator+step);
            if(dir.exists()){
                Map<String, String> molecular = molecularPack.get(step);
                boolean flag = false;
                for (Map.Entry<String, String> item : molecular.entrySet()) {
                    if(!new File(item.getValue()).exists()){
                        flag = true;
                        molecular.remove(item.getKey());
                    }
                }
                if(flag){
                    programmeMapper.updateById(programme);
                }
                ZipUtil.zip(dir.getPath(),filePath);
                file = new File(filePath);
            }else{
                molecularPack.remove(step);
                programme.getRunningState().remove(step);
                programmeMapper.updateById(programme);
                return ResponseEntity.notFound().build();
            }
        }
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        return ResponseEntity
                .ok()
                .contentLength(fileSystemResource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(fileSystemResource.getInputStream()));
    }

    public String checkIsCompletes(String programmeId, String step) {
        if(StringUtils.isEmpty(programmeId)||StringUtils.isEmpty(step)){
            throw new ServiceException("The checkIsComplete parameter cannot be empty!");
        }
        Programme programme = programmeMapper.selectById(programmeId);
        Map<String, String> state = programme.getRunningState();
        return state.get(step);
    }

    public String delStepFiles(Map<String,Object> params) {
        String programmeId = params.get("programmeId").toString();
        List<String> step = (List<String>)params.get("step");
        System.err.println(step);
        if(step != null){
            String prefixPath = getPrefixPath() + programmeId + separator;
            Programme programme = programmeMapper.selectById(programmeId);
            Map<String, Map<String, String>> molecularPack = programme.getMolecularPack();
            Map<String, String> runningState = programme.getRunningState();
            if(molecularPack == null || molecularPack.size()==0){
                if(runningState!=null){
                    runningState.clear();
                }
                FileUtil.del(prefixPath);
            }else{
                for (String s : step) {
                    molecularPack.remove(s);
                    runningState.remove(s);
                    FileUtil.del(prefixPath+s);
                    FileUtil.del(prefixPath+s+".zip");
                }
            }
            programmeMapper.updateById(programme);
        }
        return "Delete completed！";
    }

    public ResponseEntity<?> sampleFile(String fileName) throws IOException {
        File file = new File(getPrefixPath().replace("result", "sample")+fileName);
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        return ResponseEntity
                .ok()
                .contentLength(fileSystemResource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(fileSystemResource.getInputStream()));
    }
}
