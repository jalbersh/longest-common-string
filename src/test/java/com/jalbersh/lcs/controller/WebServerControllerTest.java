package com.jalbersh.lcs.controller;

import com.jalbersh.lcs.model.LCSRequest;
import com.jalbersh.lcs.model.LCSResponse;
import com.jalbersh.lcs.model.Value;
import com.jalbersh.lcs.service.LCSService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

@ContextConfiguration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebServerControllerTest {

    LCSService lcsService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    private WebServerController con;
    private MockMvc mvc;

    @Before
    public void setup() throws Exception {
        try {
            mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
            lcsService = new LCSService();
            con = new WebServerController(lcsService);
            MockitoAnnotations.initMocks(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIt() {
        assertTrue(true);
    }

    @Test
    public void testRequest_returns_result() {
        String arr[] = { "grace", "graceful", "disgraceful","gracefully","disgrace" };
        LCSRequest lcsRequest = new LCSRequest();
        Arrays.stream(arr).forEach(s -> lcsRequest.getStrings().add(new Value(s)));
        ResponseEntity<LCSResponse> relcsResponse = con.getLCS(lcsRequest);
        Assert.assertEquals(200,relcsResponse.getStatusCode().value());
        LCSResponse lcsResponse = relcsResponse.getBody();
        Assert.assertEquals(1,lcsResponse.getValues().size());
        Assert.assertTrue(lcsResponse.getValues().contains(new Value("grace")));
    }

    @Test
    public void testRequest_returns_error_message() {
        String arr[] = { "grace", "grace" };
        LCSRequest lcsRequest = new LCSRequest();
        Arrays.stream(arr).forEach(s -> lcsRequest.getStrings().add(new Value(s)));
        ResponseEntity<LCSResponse> relcsResponse = con.getLCS(lcsRequest);
        Assert.assertEquals(400,relcsResponse.getStatusCode().value());
        Assert.assertTrue(relcsResponse.getStatusCode().is4xxClientError());
        Assert.assertEquals(new Value("Input must be a set of unique strings"),relcsResponse.getBody().getValues().toArray()[0]);
    }

    @Test
    public void testRequest_returns_multiple_results() {
        String arr[] = { "gracesbaser", "gracefulbaser", "disgracefulbaser","gracefullybaser","disgracebaser" };
        LCSRequest lcsRequest = new LCSRequest();
        Arrays.stream(arr).forEach(s -> lcsRequest.getStrings().add(new Value(s)));
        ResponseEntity<LCSResponse> relcsResponse = con.getLCS(lcsRequest);
        Assert.assertEquals(200,relcsResponse.getStatusCode().value());
        LCSResponse lcsResponse = relcsResponse.getBody();
        Assert.assertEquals(2,lcsResponse.getValues().size());
        Assert.assertTrue(lcsResponse.getValues().contains(new Value("grace")));
        Assert.assertTrue(lcsResponse.getValues().contains(new Value("baser")));
    }

    @Test
    public void testBasicTest_return_cast() {
        String[] arr ={"comcast","comcastic","broadcaster"};
        LCSRequest lcsRequest = new LCSRequest();
        Arrays.stream(arr).forEach(s -> lcsRequest.getStrings().add(new Value(s)));
        ResponseEntity<LCSResponse> relcsResponse = con.getLCS(lcsRequest);
        Assert.assertEquals(200,relcsResponse.getStatusCode().value());
        LCSResponse lcsResponse = relcsResponse.getBody();
        Assert.assertEquals(1,lcsResponse.getValues().size());
        Assert.assertTrue(lcsResponse.getValues().contains(new Value("cast")));
    }

}