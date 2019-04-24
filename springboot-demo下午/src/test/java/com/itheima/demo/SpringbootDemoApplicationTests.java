package com.itheima.demo;

import com.itheima.demo.entity.User;
import com.itheima.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void getUsersByUserNameTest() {
        List<User> users = userService.getUsersByUserName("");
        System.out.println(users);
    }

}
