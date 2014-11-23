package org.mahti.herbarium.controller;

import javax.validation.Valid;
import org.mahti.herbarium.domain.User;
import org.mahti.herbarium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "signup";
        }
        userRepository.save(user);
        return "redirect:/login";
    }
    
    @RequestMapping(value="{username}",method = RequestMethod.GET)
    public String view(@PathVariable String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("name", user.getName());
        return "user";
    }
}
