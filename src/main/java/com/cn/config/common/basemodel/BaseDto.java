/**
 * 
 */
package com.cn.config.common.basemodel;

import java.util.Date;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class BaseDto {

    private String creator;  // 创建人
    private String modifier; // 修改人
    private Date   gmtCreate; // 创建时间
    private Date   gmtModify; // 修改时间
    private String isDeleted; // 是否删除:y/n
}
