package org.mahti.herbarium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("*")
public class DefaulController {
    
    @RequestMapping(method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String view() {
        return "Herbarium!";
    }
}
