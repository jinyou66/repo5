package com.itheima.demo.service.impl;

import com.itheima.demo.entity.User;
import com.itheima.demo.exception.constant.Message;
import com.itheima.demo.exception.enums.ExceptionTypeCode;
import com.itheima.demo.exception.user.UserException;
import com.itheima.demo.mapper.UserMapper;
import com.itheima.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Aaron
 * @CreateTime 2019/3/24 11:23
 * @Description TODO
 */
@Service
@SuppressWarnings("all")
public class UserServiceImpl  implements UserService {

    @Autowired
    UserMapper userMapper;
    Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public List<User> getUsersByUserName(String userName) {
            // 参数校验 如果为空 【记录日志，停止程序继续执行】
            if(StringUtils.isEmpty(userName)){
                // 记录日志
                LOGGER.error(Message.USER_EXCEPTION_NUL);
                // 参数为空抛出异常
                throw  new UserException(ExceptionTypeCode.CODE1, Message.USER_EXCEPTION_NUL);

            }
            List<User> users = userMapper.selectUsersByUserName(userName);
            return users;
    }
}
