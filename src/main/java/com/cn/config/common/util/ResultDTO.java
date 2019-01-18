package com.cn.config.common.util;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用消息信封 "timestamp": 1513651033033, "status": 500, "error": "Internal Server Error", "message": "No message
 * available", "path": "/api/users"
 *
 * @author NothingNull
 * @email NothingNull@foxmail.com
 * @date 2018/06/26 16:09
 **/
@Setter
@Getter
public class ResultDTO<T> {

    private Long    timestamp;
    private boolean successd;
    private String  error;
    private String  message;
    private String  dataVersion;
    private T       data;
    private String  poweredBy;

    public ResultDTO(){
        this.timestamp = System.currentTimeMillis();
        this.dataVersion = "1.0";
        this.poweredBy = "1165085453@qq.com";
    }

    public ResultDTO(T data){
        this.data = data;
    }
}
