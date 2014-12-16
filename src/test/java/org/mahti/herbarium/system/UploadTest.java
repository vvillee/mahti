package org.mahti.herbarium.system;

import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mahti.herbarium.Application;
import org.mahti.herbarium.repository.PlantRepository;
import org.mahti.herbarium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class UploadTest {

    private final String SIGN_UP_URI = "/signup";
    private final String USER_URI = "/users";
    private final String LOGIN_URI = "/login";

    @Autowired
    private WebApplicationContext webAppContext;
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private PlantRepository plantRepository;

    private final String USER_NAME1 = "Hemuli Kasvitieteilijä";
    private final String USER_USERNAME1 = "hemuli";
    private final String USER_DESCRIPTION1 = "Viimeinkin se kasvi on minun!";
    private final String USER_EMAIL1 = "hemuli@muumilaakso.com";
    private final String USER_PASSWORD1 = "herbaario";
    private final Long USER_ID1 = 1L;

    private final String USER_NAME2 = "Anna Tosikko";
    private final String USER_USERNAME2 = "anna";
    private final String USER_DESCRIPTION2 = "Juuri Helsingin Yliopistosta valmistunut tosikko.";
    private final String USER_EMAIL2 = "anna.virtanen@tosiyliopisto.com";
    private final String USER_PASSWORD2 = "tosikko";
    private final Long USER_ID2 = 2L;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();

        mockMvc.perform(post(SIGN_UP_URI)
                .param("name",USER_NAME1)
                .param("username",USER_USERNAME1)
                .param("email", USER_EMAIL1)
                .param("password", USER_PASSWORD1)
        );

        mockMvc.perform(post(SIGN_UP_URI)
                .param("name",USER_NAME2)
                .param("username",USER_USERNAME2)
                .param("email", USER_EMAIL2)
                .param("password", USER_PASSWORD2)
        );
    }

    @Test
    public void signInAsHemuliAndUploadOnePlant() throws Exception {

        mockMvc.perform(post("login")
                .param("username", USER_NAME1)
                .param("password", USER_PASSWORD1)
                );

        mockMvc.perform(post("upload")
                .param("name", "Daisy")
                .param("family", "Asteraceae")
                .param("genus", "Leucanthemum")
                .param("species", "L. vulgare")
                .param("file", "../img/LeucanthemumVulgare.jpg")
                );
            /*
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/upload"))
                .andReturn();
            */

        assertEquals("Plant repository size should be 1 after uploading one plant.", 1L, plantRepository.count());

    }

    /*
    @Test
    public void redirectedFromRoot() throws Exception {
        mockMvc.perform(get(API_URI))
                .andExpect(redirectedUrl(API_URI_SINGLE));
    }

    @Test
    public void responseContainsCount() throws Exception {
        mockMvc.perform(get(API_URI_SINGLE))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("count"));
    }

    @Test
    public void canPostFile() throws Exception {
        // "aarrggghh" likely wont render -- we don't care :)
        MockMultipartFile multipartFile = new MockMultipartFile("file", "aarrggghh.gif", "image/gif", "aarrggghh".getBytes());

        mockMvc.perform(fileUpload(API_URI).file(multipartFile))
                .andExpect(redirectedUrl(API_URI));
    }

    @Test
    public void countIncreasesWhenPostingAFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "aarrggghh.gif", "image/gif", "aarrggghh".getBytes());

        Long count = getCount();
        mockMvc.perform(fileUpload(API_URI).file(multipartFile))
                .andExpect(redirectedUrl(API_URI));

        assertEquals("The count of images should increase when posting a valid file.", new Long(count + 1), getCount());
    }

    @Test
    public void countDoesNotIncreaseWhenPostingANonGif() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "aarrggghh.gif", "mmm/burgerz", "aarrggghh".getBytes());

        Long count = getCount();
        mockMvc.perform(fileUpload(API_URI).file(multipartFile))
                .andExpect(redirectedUrl(API_URI));

        assertEquals("When posting a file that is not a gif (\"image/gif\"), the count of images in database should not increase.", count, getCount());
    }

    @Test
    public void canRetrieveAPostedFile() throws Exception {
        String content = UUID.randomUUID().toString().substring(0, 6);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "aarrggghh.gif", "image/gif", content.getBytes());

        mockMvc.perform(fileUpload(API_URI).file(multipartFile))
                .andExpect(redirectedUrl(API_URI));
        Long count = getCount();

        MvcResult res = mockMvc.perform(get(API_URI + "/" + count + "/content"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(content, new String(res.getResponse().getContentAsByteArray()));
        assertEquals("image/gif", res.getResponse().getContentType());
    }

    @Test
    public void previousAndNextAreSet() throws Exception {
        String content = UUID.randomUUID().toString().substring(0, 6);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "aarrggghh.gif", "image/gif", content.getBytes());

        int count = 2 + new Random().nextInt(4);

        for (int i = 0; i < count; i++) {
            mockMvc.perform(fileUpload(API_URI).file(multipartFile))
                    .andExpect(redirectedUrl(API_URI));
        }

        Long pics = getCount();

        Long retrieved = pics / 2;

        MvcResult res = mockMvc.perform(get(API_URI + "/" + retrieved))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("count", "current", "next", "previous"))
                .andReturn();

        assertEquals("When retrieving a image page, attribute current should be used as the id of the current image.", new Long(Long.parseLong("" + res.getModelAndView().getModel().get("current"))), retrieved);
        assertEquals("When retrieving a image page, attribute next should be used to indicate the next image.", new Long(Long.parseLong("" + res.getModelAndView().getModel().get("next"))), new Long(retrieved + 1));
        assertEquals("When retrieving a image page, attribute previous should be used to indicate the previous image.", new Long(Long.parseLong("" + res.getModelAndView().getModel().get("previous"))), new Long(retrieved - 1));
    }

    @Test
    public void firstImageShouldNotHavePrevious() throws Exception {
        String content = UUID.randomUUID().toString().substring(0, 6);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "aarrggghh.gif", "image/gif", content.getBytes());

        int count = 2 + new Random().nextInt(5);

        for (int i = 0; i < count; i++) {
            mockMvc.perform(fileUpload(API_URI).file(multipartFile))
                    .andExpect(redirectedUrl(API_URI));
        }

        mockMvc.perform(get(API_URI_SINGLE))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("count", "current", "next"))
                .andExpect(model().attributeDoesNotExist("previous"));
    }

    @Test
    public void lastImageShouldNotHaveNext() throws Exception {
        String content = UUID.randomUUID().toString().substring(0, 6);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "aarrggghh.gif", "image/gif", content.getBytes());

        int count = new Random().nextInt(5);

        for (int i = 0; i < count; i++) {
            mockMvc.perform(fileUpload(API_URI).file(multipartFile))
                    .andExpect(redirectedUrl(API_URI));
        }

        mockMvc.perform(get(API_URI + "/" + getCount()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("count", "current", "previous"))
                .andExpect(model().attributeDoesNotExist("next"));
    }

    private Long getCount() throws Exception {
        MvcResult res = mockMvc.perform(get(API_URI_SINGLE))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("count")).andReturn();

        return Long.parseLong("" + res.getModelAndView().getModel().get("count"));
    }
    */
}
