/**
 * 
 */
package com.cn.config.sys.service;

import com.cn.config.common.mode.AppInfoDo;

/**
 * @author Administrator
 */
public interface Iservice {

    AppInfoDo selectByPrimaryKey1(Integer id);

    AppInfoDo insert(AppInfoDo appInfoDo);
}
