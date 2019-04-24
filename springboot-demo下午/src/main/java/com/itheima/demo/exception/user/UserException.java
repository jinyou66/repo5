package com.itheima.demo.exception.user;

import com.itheima.demo.exception.SupperException;

/**
 * @Author Aaron
 * @CreateTime 2019/3/24 11:28
 * @Description TODO
 */
public class UserException extends RuntimeException{

    // 基于spring的事务只有在runtimeException 才会回滚
    private Enum  type = null;
    private  String message = " ";

    public UserException(Enum type, String message){
                 this.type=type;
                 this.message=message;
    }




}
