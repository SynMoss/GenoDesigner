package com.hmzhkj.framework.handler;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.hmzhkj.common.core.constant.HttpStatus;
import com.hmzhkj.common.core.exception.DemoModeException;
import com.hmzhkj.common.core.exception.InnerAuthException;
import com.hmzhkj.common.core.exception.ServiceException;
import com.hmzhkj.common.core.exception.auth.NotPermissionException;
import com.hmzhkj.common.core.exception.auth.NotRoleException;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.common.core.web.domain.AjaxResult;

 
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

     
    @ExceptionHandler(NotPermissionException.class)
    public AjaxResult handleNotPermissionException(NotPermissionException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("Request address' {} ', permission code verification failed'{}'", requestURI, e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "No access permission, please contact the administrator for authorization");
    }

     
    @ExceptionHandler(NotRoleException.class)
    public AjaxResult handleNotRoleException(NotRoleException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("Request address' {} ', role permission verification failed'{}'", requestURI, e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "No access permission, please contact the administrator for authorization");
    }

     
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
            HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("Request address' {} ', not supported for'{}'request", requestURI, e.getMethod());
        return AjaxResult.error(e.getMessage());
    }

     
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request)
    {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
    }

     
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("Unknown exception occurred while requesting address'{}'", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }

     
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("Requesting address'{}', a system exception occurred", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }

     
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

     
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(message);
    }

     
    @ExceptionHandler(InnerAuthException.class)
    public AjaxResult handleInnerAuthException(InnerAuthException e)
    {
        return AjaxResult.error(e.getMessage());
    }

     
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult handleDemoModeException(DemoModeException e)
    {
        return AjaxResult.error("Demonstration mode, operation not allowed");
    }
}
