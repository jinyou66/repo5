package com.itheima.demo.web.controller;

import com.itheima.demo.entity.User;
import com.itheima.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.context.ContextLoader.getCurrentWebApplicationContext;

/**
 * @Author Aaron
 * @CreateTime 2019/3/24 14:41
 * @Description TODO
 */
@RestController
@RequestMapping(value="/user")
@Api(tags = "用户管理", description = "用户信息相关")
public class UserController {
    /**
     * 返回值怎么定义取决于接口怎么定义：
     * 当前端要求返回数组
     * http://localhost:8080/user/张三
     */
        @Autowired
        UserService userService;
         @ApiOperation(value="根据用户名查询")
        @ApiImplicitParam(name="userName",value = "哈哈",required = false, allowEmptyValue = true)
        @GetMapping(value = "/{userName}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public List<User>   getUsersByUserName(@PathVariable(value="userName") String userName,HttpServletRequest request) throws Exception{
            ServletContext servletContext = request.getServletContext();
            String serverInfo = servletContext.getServerInfo();
            //  Apache Tomcat /9.0.1
            String[] split = serverInfo.split("/");
           String serverVersion = split[1].split("\\." )[0];
            //
            if(Integer.parseInt(serverVersion)<=7){
                // 如果目标服务器是tomcat7及其以下需要处理 get请求中文乱码问题
                userName.getBytes("ISO8859-1");
                userName= new String(  userName.getBytes("ISO8859-1"),"UTF-8");
            }
            List<User> users = userService.getUsersByUserName(userName);
            return users;
        }

}
