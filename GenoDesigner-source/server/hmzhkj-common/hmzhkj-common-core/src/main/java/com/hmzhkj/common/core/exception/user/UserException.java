package com.hmzhkj.common.core.exception.user;

import com.hmzhkj.common.core.exception.base.BaseException;

 
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
