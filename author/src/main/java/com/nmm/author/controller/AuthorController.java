package com.nmm.author.controller;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class AuthorController {
    //mybatis的自动注入
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @ResponseBody
    @RequestMapping("/checkUser/{username}")
    public String checkUser(@PathVariable String username) {
        Long times = sqlSessionTemplate.selectOne("UserMapper.checkUser",username);
        if (times ==null ||times < 0){
            return "{\"success\":false,\"msg\":\"该用户未登录\"}";
        }
        if (System.currentTimeMillis() - times > 1000*30){
            return "{\"success\":false,\"msg\":\"登录超时\"}";
        }
        return "{\"success\":true,\"msg\":\"登录\"}";
    }
    @ResponseBody
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(String username,String password) {
        if (username == null||password == null){
            return "{\"success\":false,\"msg\":\"用户名密码不能为空！\"}";
        }
        Long times = sqlSessionTemplate.selectOne("UserMapper.checkUser",username);
        HashMap params = new HashMap();
        params.put("username",username);
        params.put("updateTime",System.currentTimeMillis());
        if (times ==null ||times < 0){
            params.put("password",password);
            params.put("age",Math.round(Math.random()*100));
            //插入操作
            sqlSessionTemplate.insert("UserMapper.addUser",params);
        }else {
            //更新操作
            sqlSessionTemplate.insert("UserMapper.updateUser",params);
        }
        return "{\"success\":true,\"msg\":\"登录成功\"}";
    }
}
