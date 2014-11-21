package org.mahti.herbarium.controller;

import org.mahti.herbarium.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("*")
public class DefaulController {
    
    @Autowired
    private DefaultService defaultService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String view(Model model) {
        model.addAttribute("name", defaultService.getName());
        model.addAttribute("description", defaultService.getDescription());
        return "index";
    }
    
    @RequestMapping(value ="login",method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("name", defaultService.getName());
        model.addAttribute("description", defaultService.getDescription());
        return "login";
    }

    @RequestMapping(value ="register",method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("name", "Register");
        model.addAttribute("description", defaultService.getDescription());
        return "register";
    }
}
