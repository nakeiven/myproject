/**
 * 
 */
package com.cn.config.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.cn.config.common.mode.AppInfoDo;
import com.cn.config.common.util.JedisUtil;
import com.cn.config.sys.service.Iservice;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/test1")
public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);

    @Autowired
    Iservice              iservice;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request, Model model) {

        model.addAttribute("user", JSON.toJSONString(iservice.selectByPrimaryKey1(1)));
        logger.info(JSON.toJSONString(iservice.selectByPrimaryKey1(1)));
        return "index";
    }

    /**
     * 暂时没有redis服务器
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/testJedis", method = RequestMethod.GET)
    public String testJedis(HttpServletRequest request, Model model) {
        JedisUtil.setObject("lyhTest", iservice.selectByPrimaryKey1(1), 1000 * 10);
        return "index";
    }

    /**
     * 暂时没有redis服务器
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/testgetJedis", method = RequestMethod.GET)
    public String testgetJedis(HttpServletRequest request, Model model) {
        model.addAttribute("user", JedisUtil.getObject("lyhTest", AppInfoDo.class));
        return "index";
    }

}
