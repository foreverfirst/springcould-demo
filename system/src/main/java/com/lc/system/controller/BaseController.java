package com.lc.system.controller;

import com.lc.system.repository.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 描述：
 * <p>
 *
 * @author: 赵新国
 * @date: 2018/6/5 18:35
 */
public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(BaseController.class);

}
