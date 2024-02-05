package com.hmzhkj.common.core.text;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import com.hmzhkj.common.core.utils.StringUtils;

 
public class CharsetKit
{
     
    public static final String ISO_8859_1 = "ISO-8859-1";
     
    public static final String UTF_8 = "UTF-8";
     
    public static final String GBK = "GBK";

     
    public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);
     
    public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);
     
    public static final Charset CHARSET_GBK = Charset.forName(GBK);

     
    public static Charset charset(String charset)
    {
        return StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
    }

     
    public static String convert(String source, String srcCharset, String destCharset)
    {
        return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
    }

     
    public static String convert(String source, Charset srcCharset, Charset destCharset)
    {
        if (null == srcCharset)
        {
            srcCharset = StandardCharsets.ISO_8859_1;
        }

        if (null == destCharset)
        {
            destCharset = StandardCharsets.UTF_8;
        }

        if (StringUtils.isEmpty(source) || srcCharset.equals(destCharset))
        {
            return source;
        }
        return new String(source.getBytes(srcCharset), destCharset);
    }

     
    public static String systemCharset()
    {
        return Charset.defaultCharset().name();
    }
}
