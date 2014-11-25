package org.mahti.herbarium.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefaultService {
    
    @Value("${app.name}")
    private String name;
    
    @Value("${app.description}")
    private String description;

    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
}
