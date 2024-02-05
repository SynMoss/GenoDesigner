package com.hmzhkj.common.core.exception.file;

import com.hmzhkj.common.core.exception.base.BaseException;

 
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
