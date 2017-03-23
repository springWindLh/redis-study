package com.app.test;

import com.app.demo.dao.IJedisCacheDao;
import com.app.demo.dao.IUserDao;
import com.app.demo.jedis.JedisClusterFactory;
import com.app.demo.model.User;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationSpringApp.xml"})
public class RedisComponentTest {

    @Autowired
    private IJedisCacheDao jedisCacheDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private JedisClusterFactory jedisClusterFactory;

    @Test
    public void testSet() throws Exception {
//        IJedisCacheDao jedisCacheDao = new IJedisCacheDao();
//        User user = new User("张三", 25);
        List<User> users = Lists.newArrayList(new User("name1", 21), new User("name2", 22), new User("name3", 23));
//        long result = jedisCacheDao.setObjectList("a3", users);
//        jedisCacheDao.set("a7", "7777", 3);
//        jedisCacheDao.setObjectList("a9", users);
//        List<User> userList = jedisCacheDao.getObjectList("a9");
//        userDao.setObjectList("users", users);
//        jedisClusterFactory.getJedisCluster().set("name3", "tom3");
        System.out.println(userDao.getObjectList("users"));
//        System.out.println(result);
    }
}
