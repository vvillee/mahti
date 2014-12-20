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
    
    private static final String USER_NAME = "Hemuli Kasvitieteilijä";
    private static final String USER_USERNAME = "hemuli";
    private static final String USER_DESCRIPTION = "Viimeinkin se kasvi on minun!";
    private static final String USER_EMAIL = "hemuli@muumilaakso.com";
    private static final String USER_PASSWORD = "herbaario";
    
    @Test
    public void testSaveUser() {
        
        User retrieved = userRepository.findByUsername(USER_USERNAME);
        assertNull(retrieved);
        
        User user = new User();
        user.setName(USER_NAME);
        user.setUsername(USER_USERNAME);
        user.setDescription(USER_DESCRIPTION);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        
        userRepository.save(user);

        retrieved = userRepository.findByUsername(USER_USERNAME);
        assertNotNull(retrieved);
        assertEquals(USER_NAME, retrieved.getName());
        assertEquals(USER_USERNAME, retrieved.getUsername());
        assertEquals(USER_DESCRIPTION, retrieved.getDescription());
        assertEquals(USER_EMAIL, retrieved.getEmail());
        
        userRepository.delete(user);
    }
    
}
