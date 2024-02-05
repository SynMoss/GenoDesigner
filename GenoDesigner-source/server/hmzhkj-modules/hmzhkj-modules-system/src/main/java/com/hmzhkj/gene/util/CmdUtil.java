package com.hmzhkj.gene.util;

import com.hmzhkj.common.core.exception.base.BaseException;
import com.hmzhkj.common.core.utils.StringUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;


public class CmdUtil {

    public void test() {

        String cofigFilePath = "D:\\test\\2022-12-05\\1670217882966_gRNA.conf";
        String outFilePath = "D:\\test\\2022-12-05\\2022-12-05";
        String zipPath = "D:\\test\\2022-12-05";
        String zipName = "zipName";
        String s = compressGrna(cofigFilePath, outFilePath, zipPath, zipName);
        System.out.println(s);

    }

    public static String execCmdOrder(String cmdCommand) {

        if (StringUtils.isEmpty(cmdCommand)) {
            throw new RuntimeException("The conf that needs to be executed cannot be empty");
        }


        String cmd = "cmd /C " + cmdCommand;

        Process process = null;

        StringBuilder result = null;
        try {

            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {

            if (process != null) {
                process.destroy();
            }
        }
        return result.toString();
    }


    public static String callCmd(String cmdCommand) throws InterruptedException  {
        if (StringUtils.isEmpty(cmdCommand)) {
            throw new RuntimeException("The conf that needs to be executed cannot be empty");
        }


        String cmd = "cmd /C " + cmdCommand;
        Process process = null;
        BufferedReader normalReader = null;
        BufferedReader errorReader = null;
        try {

            process = Runtime.getRuntime().exec(cmd);


            normalReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorReader = new  BufferedReader(new  InputStreamReader(process.getErrorStream()));

            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = normalReader.readLine()) != null) {
                sb.append(line+"\n");
            }
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.out.print("Script file execution information ErrorStream:{}"+ errorLine+"\n");
            }

            int exitCode = process.waitFor();

            if (0 == exitCode) {
                System.out.print("Script file execution successful");

            } else {
                System.out.print("Script file execution failed:{}"+cmd);

                throw new BaseException("Script file execution failed:"+cmd);
            }
            return sb.toString();
        } catch (NullPointerException e) {
            throw new BaseException(e);
        } catch (IOException e) {
            throw new BaseException(e);
        }finally{
            if (null != normalReader) {
                try {
                    normalReader.close();
                } catch (IOException e) {
                    System.out.print("Stream file closing exception："+ e);
                }
            }
            if (null != errorReader) {
                try {
                    errorReader.close();
                } catch (IOException e) {
                    System.out.print("Stream file closing exception："+ e);
                }
            }
            if (null != process) {
                process.destroy();
            }
        }
    }

    public static String execute(String command) {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String result = null;
        try {
            File file = new File("D:\\daemonTmp");

            File tmpFile = new File("D:\\daemonTmp\\temp.tmp");
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!tmpFile.exists()) {
                tmpFile.createNewFile();
            }
            ProcessBuilder processBuilder = new ProcessBuilder()
                    .command("cmd.exe", "/c", command).inheritIO();

            processBuilder.redirectErrorStream(true);

            processBuilder.redirectOutput(tmpFile);

            processBuilder.start().waitFor();
            InputStream inputStream = new FileInputStream(tmpFile);

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            bufferedReader.close();
            bufferedReader = null;

            result = stringBuilder.toString();

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    public static String compressGrna(String cofigFilePath, String outFilePath, String zipPath, String zipNamo) {
        if(cofigFilePath.equals("cofigFilePath")){

        }else {
            copyFile(cofigFilePath,outFilePath + "/input");
        }


        OutputStream fileOutputStream = null;
        try {

            String substring =zipPath;
            String outDir = substring + "\\" + zipNamo + ".zip";
            fileOutputStream = new FileOutputStream(new File(outDir));

            System.out.print("Delete output folder");

            return outDir;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void copyFile(String resFilePath, String distFolder){

        try {
            File resFile = new File(resFilePath);

            File distFile = new File(distFolder);

            if (resFile.isDirectory()) {

                FileUtils.copyDirectoryToDirectory(resFile, distFile);

            } else if (resFile.isFile()) {

                FileUtils.copyFileToDirectory(resFile, distFile);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
