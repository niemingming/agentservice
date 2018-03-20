package com.nmm.webview.controller;

import com.nmm.webview.model.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private RestTemplate restTemplate = new RestTemplate();
    @Value("${service.author}")
    private String authorUrl;
    @Value("${service.search}")
    private String searchUrl;

    @RequestMapping("/")
    public String index(Model model) {
        return "index/index";
    }
    @RequestMapping("/login")
    public String login(String username,String password,Model model){
        MultiValueMap<String,String> params = new LinkedMultiValueMap<String, String>();
        params.add("username",username);
        params.add("password",password);
        Result res = restTemplate.postForEntity(authorUrl+"/login",params,Result.class).getBody();
        if (!res.isSuccess()){
            model.addAttribute("msg",res.getMsg());
            return "index/index";
        }
        //登录成功，查询跳转
        searchData(username,model);
        return "searchdata";
    }
    @RequestMapping("/searchDatas/{username}")
    public String searchDatas(@PathVariable String username, Model model) {
        Result res = checkLogin(username);
        if (!res.isSuccess()){
            model.addAttribute("msg",res.getMsg());
            return "index/index";
        }
        //登录成功，查询跳转
        searchData(username,model);
        return "searchdata";
    }

    private void searchData(String username, Model model) {
        String url = searchUrl + "/searchData/?username=" + username;
        String res = restTemplate.getForEntity(url,String.class).getBody();
        model.addAttribute("data",res);
        model.addAttribute("username",username);
    }

    /**
     * @description 校验登录状态
     * @param
     * @author nmm
     * @date 2018/3/20 14:41
     * @return boolean
     */
    private Result checkLogin(String username) {
        String url = authorUrl + "/checkUser/" + username;
        Result result = restTemplate.getForEntity(url,Result.class).getBody();
        return result;
    }

}
