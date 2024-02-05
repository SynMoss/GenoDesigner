package com.hmzhkj.gene.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.gene.mapper.ProgrammeMapper;
import com.hmzhkj.gene.domain.Programme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncMoleculeService {
    private final ProgrammeMapper programmeMapper;
    @Value("${path.sequence}")
    private String sequencePath;

    private final String separator = File.separator;

     
    private String getPrefixPath() {
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
     
    @NotNull
    @Async
    protected String asyncGRNA(String programmeId, String step, String prefixPath, String basePath, String resultPath, File gRNAConf, File primerConf) {
        String out = "";
                 if(prefixPath.contains(".wine")){
            String exePath = "/root/.wine/drive_c/windows/system32/molecular/gRNA_Beta_20220714/bin/";
            String execGRNA = "wine64 chopchop.exe -c "
                    + gRNAConf.getPath().replace(prefixPath, "../../result/");
            String gRNAOut = basePath + resultPath + separator + "gRNA_out.log";
            execCmdOrder(execGRNA,exePath,gRNAOut);

            String execPrimer = "wine64 Gene_gRNA_main.exe -c "
                    + primerConf.getPath().replace(prefixPath, "../../result/");
            String primerOut = basePath + resultPath + separator + "Primer_out.log";
            execCmdOrder(execPrimer,exePath,primerOut);

            out = execGRNA +" & " + execPrimer;
        }else {
            String exePath = "D:\\runBin\\bin\\";
            String execGRNA = "cmd /c "
                    +"chopchop.exe -c "+ gRNAConf.getPath();
            String gRNAOut = basePath + resultPath + separator + "gRNA_out.log";
            execCmdOrder(execGRNA,exePath,gRNAOut);


            String execPrimer = "cmd /c "
                    +"Gene_gRNA_main.exe -c "+ primerConf.getPath();
            String primerOut = basePath + resultPath + separator + "Primer_out.log";
            execCmdOrder(execPrimer,exePath,primerOut);

            out = execGRNA +" & " + execPrimer;
        }
        packResult(programmeId, step,"gRNA");
        return out;
    }

    @NotNull
    @Async
    protected String asyncPrimerPrediction(String programmeId, String step, String prefixPath, String basePath, String resultPath, File inputConf) {
        String out = "";
                 if(prefixPath.contains(".wine")){
            String exePath = "/root/.wine/drive_c/windows/system32/molecular/Primer_Prediction_From_GenBank_version_20221214/GUI/";
            String execInput = "wine64 Primer_Prediction_From_GenBank_v4.exe -conf "
                    + inputConf.getPath().replace(prefixPath, "../../result/");
            String gRNAOut = basePath + resultPath+ separator + "Primer_Prediction_out.log";
            execCmdOrder(execInput,exePath,gRNAOut);

            out = execInput;
        }else {
            String exePath = "D:\\runBin\\Primer_Prediction_From_GenBank_version_20221214\\GUI\\";
            String execInput = "cmd /c Primer_Prediction_From_GenBank_v4.exe -conf "+ inputConf.getPath();
            String gRNAOut = basePath + resultPath+ separator + "Primer_Prediction_out.log";

            execCmdOrder(execInput,exePath,gRNAOut);
            out = execInput;
        }
        packResult(programmeId,step,"Primer_Prediction");
        return out;
    }

    @NotNull
    @Async
    protected String asyncGeneKnockout(String programmeId, String step, String prefixPath, String basePath, String resultPath, File inputConf) {
        String out = "";
                 if(prefixPath.contains(".wine")){
            String exePath = "/root/.wine/drive_c/windows/system32/molecular/Gene_Knockout_Beta_Kan/bin/";
            String execInput = "wine64 Gene_Knockout.exe -c " +
                    inputConf.getPath().replace(prefixPath, "../../result/");
            String gRNAOut = basePath + resultPath + separator + "Gene_Knockout_out.log";
            execCmdOrder(execInput,exePath,gRNAOut);

            out = execInput;
        }else {
            String exePath = "D:\\runBin\\Gene_Knockout_Beta_Kan\\bin\\";
            String execInput = "cmd /c Gene_Knockout.exe -c "+ inputConf.getPath();
            String gRNAOut = basePath + resultPath + separator + "Gene_Knockout_out.log";
            execCmdOrder(execInput,exePath,gRNAOut);
            out = execInput;
        }
        packResult(programmeId, step,"Gene_Knockout");
        return out;
    }

     
    private void execCmdOrder(String cmdCommand,String exePath,String outPath) {
        System.out.println("Executing commands...");
        if (StringUtils.isEmpty(cmdCommand)){
            throw new RuntimeException("The command that needs to be executed cannot be empty");
        }

                 String[]command= cmdCommand.split("\\s+") ;
        Process process=null;
        try {
                          System.err.println(Arrays.toString(command));
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            if(exePath != null){
                processBuilder.directory(new File(exePath));
            }
            process = processBuilder.start();

                         while (process.isAlive()) {
                process.waitFor(10L, TimeUnit.MINUTES);
            }
        } catch (InterruptedException | IOException e) {
            log.error(e.getMessage());
        }finally {

            if (process != null) {
                process.destroy();
            }
            System.out.println("completion of enforcement!");
        }
    }


    private void packResult(String programmeId,String step,String type){
        System.out.println("Packaging...");
        String prefixPath = getPrefixPath();
        String basePath = prefixPath + programmeId + separator + step + separator + type + separator;
        FileUtil.del(basePath+"config");
        FileUtil.del(basePath+"data");
        File zip = ZipUtil.zip(prefixPath + programmeId + separator + step);

        Programme one = programmeMapper.selectById(programmeId);
        Map<String, Map<String,String>> molecularPack = one.getMolecularPack();
        if(molecularPack == null ){
            molecularPack = new HashMap<>();
            molecularPack.put(step,new HashMap<>());
        }else if(molecularPack.get(step)==null){
            molecularPack.put(step,new HashMap<>());
        }
        molecularPack.get(step).put(type, basePath + "result");
        one.setMolecularPack(molecularPack);
        one.getRunningState().put(step, "1");
        programmeMapper.updateById(one);

        System.out.println("Packaging completed!");
    }
}
