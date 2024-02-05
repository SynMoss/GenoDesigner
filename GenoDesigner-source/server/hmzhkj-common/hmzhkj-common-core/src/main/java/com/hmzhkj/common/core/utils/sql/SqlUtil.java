package com.hmzhkj.common.core.utils.sql;

import com.hmzhkj.common.core.exception.UtilException;
import com.hmzhkj.common.core.utils.StringUtils;

 
public class SqlUtil
{
     
    public static String SQL_REGEX = "select |insert |delete |update |drop |count |exec |chr |mid |master |truncate |char |and |declare ";

     
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

     
    public static String escapeOrderBySql(String value)
    {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value))
        {
            throw new UtilException("The parameters do not meet the specifications and cannot be queried");
        }
        return value;
    }

     
    public static boolean isValidOrderBySql(String value)
    {
        return value.matches(SQL_PATTERN);
    }

     
    public static void filterKeyword(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return;
        }
        String[] sqlKeywords = StringUtils.split(SQL_REGEX, "\\|");
        for (String sqlKeyword : sqlKeywords)
        {
            if (StringUtils.indexOfIgnoreCase(value, sqlKeyword) > -1)
            {
                throw new UtilException("There is a risk of SQL injection in parameters");
            }
        }
    }
}
