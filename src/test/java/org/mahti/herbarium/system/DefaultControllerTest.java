package org.mahti.herbarium.system;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mahti.herbarium.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class DefaultControllerTest {
    
    private final String API_URI = "/";
    
    @Value("${app.name}")
    private String name;

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }
    
    @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get(API_URI))
                .andExpect(status().isOk());
    }


    @Test
    public void responseTypeApplicationJson() throws Exception {
        mockMvc.perform(get(API_URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML+";charset=UTF-8"));
    }

    @Test
    public void responseContainsTextAwesome() throws Exception {
        MvcResult res = mockMvc.perform(get(API_URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML+";charset=UTF-8"))
                .andReturn();

        String content = res.getResponse().getContentAsString();
        Assert.assertTrue(content.contains(name));

    }
}
