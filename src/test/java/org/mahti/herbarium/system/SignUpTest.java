package org.mahti.herbarium.system;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mahti.herbarium.Application;
import org.mahti.herbarium.domain.User;
import org.mahti.herbarium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class SignUpTest {

    private final String SIGN_UP_URI = "/signup";
    private final String USER_URI = "/users";
    private final String LOGIN_URI = "/login";
    
    private final String MEDIA_TYPE_TEXT_HTML_UTF8 = MediaType.TEXT_HTML_VALUE + ";charset=UTF-8";

    @Autowired
    private WebApplicationContext webAppContext;
    
    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;
    
    private final String USER_NAME = "Hemuli Kasvitieteilijä";
    private final String USER_USERNAME = "hemuli";
    private final String USER_DESCRIPTION = "Viimeinkin se kasvi on minun!";
    private final String USER_EMAIL = "hemuli@muumilaakso.com";
    private final String USER_PASSWORD = "herbaario";
    
    @Before
    public void setUp() {
        
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .build();
    
    }
    
    @Test
    public void signUpStatusOk() throws Exception {
    
        mockMvc.perform(get(SIGN_UP_URI))
                .andExpect(status().isOk());
    
    }


    @Test
    public void signUpResponseTypeTextHtml() throws Exception {
        
        mockMvc.perform(get(SIGN_UP_URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MEDIA_TYPE_TEXT_HTML_UTF8));
    
    }
    
    
    @Test
    public void signUpResponseContainsSignUpForm() throws Exception {
        
        MvcResult res = mockMvc.perform(get(SIGN_UP_URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MEDIA_TYPE_TEXT_HTML_UTF8))
                .andReturn();

        String content = res.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("sign-up-form"));

    }
    
    @Test
    public void loginStatusOk() throws Exception {
    
        mockMvc.perform(get(LOGIN_URI))
                .andExpect(status().isOk());
    
    }


    @Test
    public void loginResponseTypeTextHtml() throws Exception {
        
        mockMvc.perform(get(LOGIN_URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MEDIA_TYPE_TEXT_HTML_UTF8));
    
    }
    
    
    @Test
    public void loginResponseContainsLoginForm() throws Exception {
        
        MvcResult res = mockMvc.perform(get(LOGIN_URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MEDIA_TYPE_TEXT_HTML_UTF8))
                .andReturn();

        String content = res.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("login-form"));

    }
    
    
    @Test
    public void testSignUp() throws Exception {
        
        mockMvc.perform(post(USER_URI)
                .param("name",USER_NAME)
                .param("username",USER_USERNAME)
                .param("email", USER_EMAIL)
                .param("password", USER_PASSWORD)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andReturn();
        
        User user = userRepository.findByUsername(USER_USERNAME);
        userRepository.delete(user);

    }
    
}
