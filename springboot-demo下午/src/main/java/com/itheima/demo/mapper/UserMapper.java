package com.itheima.demo.mapper;

import com.itheima.demo.entity.User;
import com.itheima.demo.mapper.sql.UserMapperSqlProvider;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author Aaron
 * @CreateTime 2019/3/24 11:04
 * @Description TODO
 */
public interface UserMapper {
    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    @SelectProvider(type = UserMapperSqlProvider.class,method = "selectUsersByUserName")
    @ResultType(User.class)
    List<User> selectUsersByUserName(String userName);

}
