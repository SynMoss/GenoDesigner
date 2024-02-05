package com.hmzhkj.common.core.utils;

import com.hmzhkj.common.core.exception.file.FileNameLengthLimitExceededException;
import com.hmzhkj.common.core.exception.file.FileSizeLimitExceededException;
import com.hmzhkj.common.core.exception.file.InvalidExtensionException;
import com.hmzhkj.common.core.utils.file.FileTypeUtils;
import com.hmzhkj.common.core.utils.file.MimeTypeUtils;
import com.hmzhkj.common.core.utils.uuid.Seq;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipParameters;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

 
public class FileUploadUtils {
     
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

     
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

     
    public static final String upload(String baseDir, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    public static String uploadMultipartFile(MultipartFile file, String path, String fileName) {
                 if (null != file) {
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            String filePath = path + File.separator + fileName;
            File localFile = new File(filePath);
            try {
                file.transferTo(localFile);
            } catch (IOException e) {
                return null;
            }
                         return filePath;
        }
        return null;
    }

    private static ZipArchiveInputStream getZipFile(File zipFile) throws Exception {
        return new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
    }

    public static String decompress(File gzFile, String targetFolderPath) throws IOException {
        byte[] buffer = new byte[1024*1024];
        FileInputStream fi = new FileInputStream(gzFile);
        String fileName = gzFile.getName().replace(".gz", "");
        GZIPInputStream gzis = new GZIPInputStream(fi);
        FileOutputStream out = new FileOutputStream(targetFolderPath + "/" + fileName);
        int totalSize;
        while ((totalSize = gzis.read(buffer)) > 0) {
            out.write(buffer, 0, totalSize);
        }
        fi.close();
        out.close();
        gzis.close();
        return fileName;
    }
         public static String compress(File inputFile,String targetPath) throws IOException {
        byte[] buffer = new byte[1024*1024];
        String fileName = inputFile.getName()+".gz";
        String foPath = targetPath+"/"+fileName;
        FileOutputStream fo = new FileOutputStream(foPath);
        GZIPOutputStream gos = new GZIPOutputStream(fo);
        FileInputStream fi = new FileInputStream(inputFile);
        int totalSize;
        while ((totalSize = fi.read(buffer)) > 0) {
            gos.write(buffer, 0, totalSize);
        }
        fi.close();
        gos.close();
        fo.close();
        return foPath;
    }
    public static boolean unzip(File zipFile, String descDir) {
        try (ZipArchiveInputStream inputStream = getZipFile(zipFile)) {
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            ZipArchiveEntry entry = null;
            while ((entry = inputStream.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = new File(descDir, entry.getName());
                    directory.mkdirs();
                } else {
                    OutputStream os = null;
                    try {
                        os = new BufferedOutputStream(new FileOutputStream(new File(descDir, entry.getName())));
                                                 IOUtils.copy(inputStream, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
            final File[] files = pathFile.listFiles();
            if (files != null && files.length == 1 && files[0].isDirectory()) {
                                 FileUtils.copyDirectory(files[0], pathFile);
                                 boolean isValid = files[0].getPath().contains("/data/www/");
                if (isValid) {
                    FileUtils.forceDelete(files[0]);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

     
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException {
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file);

        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        file.transferTo(Paths.get(absPath));
        return getPathFileName(fileName);
    }

     
    public static final String extractFilename(MultipartFile file) {
        return StringUtils.format("{}/{}_{}.{}", DateUtils.datePath(),
                FilenameUtils.getBaseName(file.getOriginalFilename()), Seq.getId(Seq.uploadSeqType), FileTypeUtils.getExtension(file));
    }

    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc.isAbsolute() ? desc : desc.getAbsoluteFile();
    }

    private static final String getPathFileName(String fileName) throws IOException {
        String pathFileName = "/" + fileName;
        return pathFileName;
    }

     
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = FileTypeUtils.getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
                        fileName);
            } else {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }
    }

     
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}