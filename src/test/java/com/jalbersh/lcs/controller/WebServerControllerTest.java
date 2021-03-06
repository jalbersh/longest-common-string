package com.jalbersh.lcs.controller;

import com.jalbersh.lcs.error.LCSErrorResponse;
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
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        ResponseEntity relcsResponse = con.getLCS(lcsRequest);
        Assert.assertEquals(200,relcsResponse.getStatusCode().value());
        LCSResponse lcsResponse = (LCSResponse)relcsResponse.getBody();
        Assert.assertEquals(1,lcsResponse.getValues().size());
        Assert.assertTrue(lcsResponse.getValues().contains(new Value("grace")));
    }

    @Test
    public void testRequest_returns_error_message() {
        String arr[] = { "grace", "grace" };
        LCSRequest lcsRequest = new LCSRequest();
        Arrays.stream(arr).forEach(s -> lcsRequest.getStrings().add(new Value(s)));
        ResponseEntity relcsResponse = con.getLCS(lcsRequest);
        Assert.assertEquals(400,relcsResponse.getStatusCode().value());
        Assert.assertTrue(relcsResponse.getStatusCode().is4xxClientError());
        LCSErrorResponse lcsErrorResponse = (LCSErrorResponse)relcsResponse.getBody();
        Assert.assertEquals("Input must be a set of unique strings",lcsErrorResponse.getMessage());
        Assert.assertEquals(400,lcsErrorResponse.getError());
    }

    @Test
    public void testRequest_returns_multiple_results_in_order() {
        String arr[] = { "gracesbaser", "gracefulbaser", "disgracefulbaser","gracefullybaser","disgracebaser" };
        LCSRequest lcsRequest = new LCSRequest();
        Arrays.stream(arr).forEach(s -> lcsRequest.getStrings().add(new Value(s)));
        ResponseEntity relcsResponse = con.getLCS(lcsRequest);
        Assert.assertEquals(200,relcsResponse.getStatusCode().value());
        LCSResponse lcsResponse = (LCSResponse)relcsResponse.getBody();
        Assert.assertEquals(2,lcsResponse.getValues().size());
        Assert.assertTrue(lcsResponse.getValues().contains(new Value("grace")));
        Assert.assertTrue(lcsResponse.getValues().contains(new Value("baser")));
        System.out.println("got "+lcsResponse.getValues());
        // confirm order
        Set<Value> expected = new HashSet();
        expected.add(new Value("baser"));
        expected.add(new Value("grace"));
        expected = expected.stream().sorted().collect(Collectors.toSet());
        Assert.assertEquals(expected,lcsResponse.getValues());
    }

    @Test
    public void testBasicTest_return_cast() {
        String[] arr ={"comcast","comcastic","broadcaster"};
        LCSRequest lcsRequest = new LCSRequest();
        Arrays.stream(arr).forEach(s -> lcsRequest.getStrings().add(new Value(s)));
        ResponseEntity relcsResponse = con.getLCS(lcsRequest);
        Assert.assertEquals(200,relcsResponse.getStatusCode().value());
        LCSResponse lcsResponse = (LCSResponse)relcsResponse.getBody();
        Assert.assertEquals(1,lcsResponse.getValues().size());
        Assert.assertTrue(lcsResponse.getValues().contains(new Value("cast")));
    }

}