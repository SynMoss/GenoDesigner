package com.hmzhkj.gene.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

 

public class MultipartFileToFile {

     
    public static String saveMultipartFile(MultipartFile file, String targetDirPath) {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            return null;
        } else {

             
            String originalFilename = file.getOriginalFilename();
             
            String fileFormat = originalFilename.substring(originalFilename.lastIndexOf("."));

            toFile = new File(targetDirPath);

            String absolutePath = null;
            try {
                absolutePath = toFile.getCanonicalPath();

                 
                String dirPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
                File dir = new File(dirPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                InputStream ins = file.getInputStream();

                inputStreamToFile(ins, toFile);
                ins.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


            return absolutePath;
        }

    }

         private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     
    public static void deleteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }
}
