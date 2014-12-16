package org.mahti.herbarium.integration;

import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mahti.herbarium.Application;
import org.mahti.herbarium.domain.Plant;
import org.mahti.herbarium.repository.PlantRepository;
import org.mahti.herbarium.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class PlantServiceTest {
    
    @Autowired
    private PlantRepository plantRepository;
    
    @Autowired
    private PlantService plantService;
    
    private final static String COMMENT = "kommentti";
    
    @Test
    public void testAddComment(){
        
        Plant plant = new Plant();
        plant.setName("Voikukka");
        plantRepository.saveAndFlush(plant);
        
        plantService.addComment(plant.getId(), COMMENT, "test");
        
        Plant commentedPlant = plantRepository.findOne(plant.getId());
        assertEquals(commentedPlant.getComments().iterator().next().getComment(), COMMENT);
    
    }
    
}
