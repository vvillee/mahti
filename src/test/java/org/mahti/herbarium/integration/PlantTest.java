package org.mahti.herbarium.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mahti.herbarium.Application;
import org.mahti.herbarium.domain.Plant;
import org.mahti.herbarium.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class PlantTest {
    
    @Autowired
    private PlantRepository plantRepository;

    /*
    @Before
    public static void setUp() {
    }
    */

    @Test
    public void sizeOfRepository() throws Exception {
        assertEquals("Repository size in the beginning.", 0, plantRepository.count());

        MockMultipartFile multipartFile = new MockMultipartFile("file", "../img/LeucanthemumVulgare.jpg", "image/jpg", "LeucanthemumVulgare".getBytes());

        Plant plant = new Plant();
        plant.setName("Daisy");
        plant.setFamily("Asteraceae");
        plant.setGenus("Leucanthemum");
        plant.setSpecies("L. vulgare");
        plant.setContent(multipartFile.getBytes());
        plantRepository.save(plant);

        assertEquals("Repository size after first plant is saved to the repository.", 1, plantRepository.count());

        multipartFile = new MockMultipartFile("file", "../img/AnemoneNemorosa.jpg", "image/jpg", "AnemoneNemorosa".getBytes());

        plant = new Plant();
        plant.setName("Windflower");
        plant.setFamily("Ranunculales");
        plant.setGenus("Anemone");
        plant.setSpecies("A. nemorosa");
        plant.setContent(multipartFile.getBytes());
        plantRepository.save(plant);

        assertEquals("Repository size after second plant is saved to the repository.", 2, plantRepository.count());
        
    }

    @Test
    public void firstPlantHasRightFieldsAndContent() throws Exception {
        Plant plant = plantRepository.findOne(1L);
        // Uncomment when you know how to compare contents
        // MockMultipartFile multipartFile = new MockMultipartFile("file", "../img/LeucanthemumVulgare.jpg", "image/jpg", "LeucanthemumVulgare".getBytes());
        // assertTrue("Contents not equal.", multipartFile.getBytes().equals(plant.getContent()));

        assertEquals("Check name", "Daisy", plant.getName());
        assertEquals("Check family.", "Asteraceae", plant.getFamily());
        assertEquals("Check genus.", "Leucanthemum", plant.getGenus());
        assertEquals("Check species.", "L. vulgare", plant.getSpecies());
        // Just test if plant has some content
        assertTrue("Does content exist?", plant.getContent().length > 0);
    }

    @Test
    public void secondPlantHasRightFieldsAndContent() {
        Plant plant = plantRepository.findOne(2L);

        assertEquals("Check name", "Windflower", plant.getName());
        assertEquals("Check family.", "Ranunculales", plant.getFamily());
        assertEquals("Check genus.", "Anemone", plant.getGenus());
        assertEquals("Check species.", "A. nemorosa", plant.getSpecies());
        assertTrue("Does content exist?", plant.getContent().length > 0);
    }

    @After
    public void clearPlantRepository() {
        plantRepository.deleteAll();
    }


}
