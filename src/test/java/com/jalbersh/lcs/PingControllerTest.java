package com.jalbersh.lcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jalbersh.lcs.controller.PingController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(locations={"classpath:app-context.xml"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class PingControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    private PingController con;

    private String token;
    private UserDetails userDetails;

    private MockMvc mvc;

    @Before
    public void setup() throws Exception {
        try {
            mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
            con = new PingController();
            MockitoAnnotations.initMocks(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testControllerPingSucceeds() throws Exception {
        String actual = con.ping();
        assertThat(actual.startsWith("Ping successful. Server Time:"));
    }

    @Test
    public void testPingSucceeds() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletRequestBuilder request = get("/ping")
                .contentType(MediaType.TEXT_PLAIN);
        this.mvc.perform(request)
                .andExpect(status().isOk());
    }

}
