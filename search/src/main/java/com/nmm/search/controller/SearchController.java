package com.nmm.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

    @ResponseBody
    @RequestMapping("/searchData")
    public String searchData(@RequestParam(name = "username") String name){
        return name + System.currentTimeMillis();
    }

}
