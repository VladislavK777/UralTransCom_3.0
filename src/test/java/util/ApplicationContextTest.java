package util;

import com.uraltranscom.distributionpark.util.ZookeeperSettingHolder;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
public class ApplicationContextTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testGetZookeeperSettingHolder() throws Exception {
        ZookeeperSettingHolder zookeeperSettingHolder = applicationContext.getBean("zkSettingsHolder", ZookeeperSettingHolder.class);
        Assert.assertNotNull(zookeeperSettingHolder);
        Assert.assertEquals("postgres", zookeeperSettingHolder.getUser());
    }

    @Test
    public void testGetConnection() throws SQLException {
        DataSource dataSource = applicationContext.getBean("dataSource", DataSource.class);
        Assert.assertNotNull(dataSource);
    }
}
