/**
 * 
 */
package com.cn.config.common.basemodel;

import lombok.Data;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Administrator
 */
@Data
public class BillQueryDto {

    /**
     * 查询类型
     */
    private String     conditionType;

    /**
     * 查询条件
     */
    private JSONObject condition;
}
