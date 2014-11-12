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

    public String getName(){
        return name;
    }
}
