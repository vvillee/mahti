package org.mahti.herbarium.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mahti.herbarium.Application;
import org.mahti.herbarium.domain.User;
import org.mahti.herbarium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class UserTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void testSaveUser() {
        
        User retrieved = userRepository.findByUsername("hemuli");
        assertNull(retrieved);
        
        User user = new User();
        user.setName("Hemuli Kasvitieteilijä");
        user.setUsername("hemuli");
        user.setDescription("Viimeinkin se kasvi on minun!");
        user.setEmail("hemuli@muumilaakso.com");
        user.setPassword("herbaario");
        
        userRepository.save(user);

        retrieved = userRepository.findByUsername("hemuli");
        assertNotNull(retrieved);
        assertEquals("Hemuli Kasvitieteilijä", retrieved.getName());
        
        userRepository.delete(user);
    }
    
}
