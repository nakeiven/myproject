/**
 * 
 */
package com.cn.config.common.enums;

import lombok.Getter;

/**
 * @author Administrator
 */
@Getter
public enum YesOrNoEnum {
    /**
     * 否
     */
    YES("y", "是"),
    /**
     * 是
     */
    NO("n", "否");

    private String code;
    private String name;

    private YesOrNoEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

}
