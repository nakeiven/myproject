/**
 * 
 */
package myproject;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.cn.config.common.mode.AppInfoDo;
import com.cn.config.admin.service.Iservice;

/**
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextHierarchy({ @ContextConfiguration(locations = "classpath:applicationContext.xml") })
public class TestMyBatis {

    private static Logger logger = Logger.getLogger(TestMyBatis.class);
    @Resource
    Iservice              iservice;

    public AppInfoDo init() {
        AppInfoDo appInfoDo = new AppInfoDo();
        appInfoDo.setAppCode("11111");
        appInfoDo.setAppSecret(UUID.randomUUID().toString());
        return appInfoDo;
    }

    @Before
    public void before() {
    }

    @Test
    public void selectByPrimaryKey() {
        iservice.selectByPrimaryKey1(1);
    }

    @Test
    public void testInsert() {
        try {
            iservice.insert(init());
        } catch (Exception e) {
            logger.info("", e);
        }
    }

    @After
    public void after() {
    }
}
