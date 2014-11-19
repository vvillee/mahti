package org.mahti.herbarium.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author ville
 */
@Service
public class DefaultService {
    
    @Value("${name}")
    private String name;
    
    @Value("${description}")
    private String description;

    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
}
