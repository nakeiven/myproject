package com.cn.config.common.util;

import javax.xml.bind.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author NothingNull
 * @email NothingNull@foxmail.com
 * @date 2018/06/26 16:48
 */
@EnableWebMvc
@ControllerAdvice
@ResponseBody
public class ExceptionHandle {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 未知错误 - 记录LOG
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResultDTO<?> handleException(Exception e) {
        LOGGER.error("系统异常", e);
        return ResultUtil.error(e.getMessage());
    }

    /**
     * 404 - Not Found
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler()
    public ResultDTO<?> handleNotFoundException(NoHandlerFoundException e) {
        LOGGER.error("页面未找到", e);
        return ResultUtil.error(e.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultDTO<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        LOGGER.error("参数解析失败", e);
        return ResultUtil.error(e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultDTO<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        LOGGER.error("不支持当前请求方法", e);
        return ResultUtil.error(e.getMessage());
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResultDTO<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        LOGGER.error("不支持当前媒体类型", e);
        return ResultUtil.error(e.getMessage());
    }

    /**
     * 校验异常 - Validation Error
     */
    @ExceptionHandler({ ValidationException.class })
    public ResultDTO<?> handleValidationException(ValidationException e) {
        LOGGER.error("参数非法", e);
        return ResultUtil.error(e.getMessage());
    }

    /**
     * 校验异常 - Validation Error
     */
    @ExceptionHandler({ BindException.class })
    public ResultDTO<?> handleBindException(BindException e) {
        LOGGER.error("参数非法", e);
        return ResultUtil.error(e.getMessage());
    }
}
