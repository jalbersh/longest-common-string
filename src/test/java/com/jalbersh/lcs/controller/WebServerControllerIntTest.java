package com.jalbersh.lcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jalbersh.lcs.error.LCSErrorResponse;
import com.jalbersh.lcs.model.LCSResponse;
import com.jalbersh.lcs.model.Value;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;

@SpringBootTest
@AutoConfigureWebTestClient(timeout = "100000") // Extend timeout to 100 seconds
@DisplayName("Test Integration cases for WebServerController")
public class WebServerControllerIntTest {

    @DisplayName("LCSResponse status OK")
    @Test
    public void testLCSRequest_LCSResponse_OK() {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/lcs");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        try {
            String json = "{\"setOfStrings\": [{\"value\": \"comcast\"},{\"value\": \"comcastic\"},{\"value\": \"broadcaster\"}]}";
            HttpEntity httpEntity = new StringEntity(json);
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = client.execute(httpPost);
            Assert.assertEquals(200, response.getStatusLine().getStatusCode());
            String result = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            LCSResponse lcsResponse = mapper.readValue(result, LCSResponse.class);
            Assert.assertTrue(lcsResponse.getValues().contains(new Value("cast")));
            client.close();
        } catch (Exception e) {
            System.out.println("Exception encountered: "+e.getMessage());
        }
    }

    @DisplayName("LCSResponse status NOT OK")
    @Test
    public void testLCSRequest_LCSResponse_NOT_OK() {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/lcs");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        try {
            String json = "{\"setOfStrings\": [{\"value\": \"comcast\"},{\"value\": \"comcast\"},{\"value\": \"comcast\"}]}";
            HttpEntity httpEntity = new StringEntity(json);
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = client.execute(httpPost);
            Assert.assertEquals(400, response.getStatusLine().getStatusCode());
            String result = EntityUtils.toString(response.getEntity());
            System.out.println("got result="+result);
            ObjectMapper mapper = new ObjectMapper();
            LCSErrorResponse lcsErrorResponse = mapper.readValue(result, LCSErrorResponse.class);
            Assert.assertEquals("Input must be a set of unique strings",lcsErrorResponse.getMessage());
            client.close();
        } catch (Exception e) {
            System.out.println("Exception encountered: "+e.getMessage());
        }
    }

}
