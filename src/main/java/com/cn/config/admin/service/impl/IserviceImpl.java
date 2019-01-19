/**
 * 
 */
package com.cn.config.admin.service.impl;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.config.common.mapper.AppInfoDoMapper;
import com.cn.config.common.mode.AppInfoDo;
import com.cn.config.admin.service.Iservice;

/**
 * @author Administrator
 */
@Service
public class IserviceImpl implements Iservice {

    @Autowired
    AppInfoDoMapper appInfoDoMapper;

    @Override
    public AppInfoDo selectByPrimaryKey1(Integer id) {
        return appInfoDoMapper.selectByPrimaryKey(id);
    }

    @Override
    public AppInfoDo insert(AppInfoDo appInfoDo) {
        appInfoDoMapper.insert(appInfoDo);
        throw new ValidationException("我故意的");
    }
}
