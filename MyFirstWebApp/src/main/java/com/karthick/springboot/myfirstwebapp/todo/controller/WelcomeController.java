package com.karthick.springboot.myfirstwebapp.todo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("name")
public class WelcomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String gotoWelcomePage(ModelMap modelMap){
        modelMap.put("name",getLoggedinUserName());
        return "welcome";
    }

    private String getLoggedinUserName(){
        Authentication authentication =SecurityContextHolder.getContext()
                .getAuthentication();
        return authentication.getName();
    }


}
