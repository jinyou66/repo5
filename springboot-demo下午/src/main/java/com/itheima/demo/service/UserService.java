package com.itheima.demo.service;

import com.itheima.demo.entity.User;

import java.util.List;

/**
 * 用户管理业务层接口
 * @Author Aaron
 * @CreateTime 2019/3/24 11:00
 * @Description TODO
 */
public interface UserService {

    /**
     * 业务： 根据用户名查询用户。 【用户名是否可以重复：重复】
     */
    /**
     * 根据用户名精确查询用户
     * @param userName   用户名参数可以重复
     * @return
     */
    List<User> getUsersByUserName(String userName);
}
