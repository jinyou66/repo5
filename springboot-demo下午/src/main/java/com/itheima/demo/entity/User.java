package com.itheima.demo.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author Aaron
 * @CreateTime 2019/3/24 10:48
 * @Description TODO
 */
@Data
@ToString
public class User  implements Serializable {
    private  Long   userId;
    private  String  userName;
    private  String  password;
    private  String  email;
}
