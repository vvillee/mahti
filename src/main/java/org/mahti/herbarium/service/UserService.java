package org.mahti.herbarium.service;

import java.util.HashMap;
import java.util.List;
import org.mahti.herbarium.domain.User;
import org.mahti.herbarium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User getAuthenticatedUser(){        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName());        
    }
    
    public HashMap<Long, String> getUserIdUsernameRelation(List<Long> userIds){
        HashMap<Long, String> userIdUsernameRelation = new HashMap();
        for (User user : userRepository.findAll(userIds)){
            userIdUsernameRelation.put(user.getId(), user.getUsername());
        }
        return userIdUsernameRelation;
    }
}
