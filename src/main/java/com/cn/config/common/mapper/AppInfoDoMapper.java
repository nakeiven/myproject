package com.cn.config.common.mapper;

import com.cn.config.common.mode.AppInfoDo;
import com.cn.config.common.mode.AppInfoDoExample;
import java.util.List;

public interface AppInfoDoMapper {
    long countByExample(AppInfoDoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppInfoDo record);

    int insertSelective(AppInfoDo record);

    List<AppInfoDo> selectByExample(AppInfoDoExample example);

    AppInfoDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppInfoDo record);

    int updateByPrimaryKey(AppInfoDo record);
}