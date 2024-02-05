package com.hmzhkj.common.core.utils.file;

 
public class MimeTypeUtils
{
    public static final String IMAGE_PNG = "image/png";

    public static final String IMAGE_JPG = "image/jpg";

    public static final String IMAGE_JPEG = "image/jpeg";

    public static final String IMAGE_BMP = "image/bmp";

    public static final String IMAGE_GIF = "image/gif";

    public static final String[] IMAGE_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png" };

    public static final String[] FLASH_EXTENSION = { "swf", "flv" };

    public static final String[] MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
            "asf", "rm", "rmvb" };

    public static final String[] VIDEO_EXTENSION = { "mp4", "avi", "rmvb" };

    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
                         "fasta","fna","fa","fas","txt","gb","gbk","gp","seq","gbank","gbs","gbwithparts","gen","genbank","gbff","dna","ape","xml","sbol",
                         "bmp", "gif", "jpg", "jpeg", "png",
                         "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
                         "rar", "zip", "gz", "bz2",
                         "mp4", "avi", "rmvb",
                         "pdf" };

    public static String getExtension(String prefix)
    {
        switch (prefix)
        {
            case IMAGE_PNG:
                return "png";
            case IMAGE_JPG:
                return "jpg";
            case IMAGE_JPEG:
                return "jpeg";
            case IMAGE_BMP:
                return "bmp";
            case IMAGE_GIF:
                return "gif";
            default:
                return "";
        }
    }
}
