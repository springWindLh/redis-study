package com.app.demo.dao.impl;

import com.app.demo.dao.IUserDao;
import com.app.demo.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by lh on 2017/3/21.
 */
@Repository("userDao")
public class UserDaoImpl extends JedisClusterCacheDaoImpl<User> implements IUserDao{
}
