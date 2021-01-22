package com.jalbersh.lcs.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jalbersh.lcs.error.GenericException;
import com.jalbersh.lcs.error.LCSErrorResponse;
import com.jalbersh.lcs.model.LCSResponse;
import com.jalbersh.lcs.model.Value;
import jdk.jfr.StackTrace;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.server.ResponseStatusException;

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

    @DisplayName("NOT LCSRequest status NOT OK")
    @Test
    public void testNotLCSRequest_ErrorResponse_NOT_OK() {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/lcs");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        try {
            String json = "{\"goobage\": [{\"value\": \"comcast\"},{\"value\": \"comcast\"},{\"value\": \"comcast\"}]}";
            HttpEntity httpEntity = new StringEntity(json);
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = client.execute(httpPost);
            Assert.assertEquals(400, response.getStatusLine().getStatusCode());
            String result = EntityUtils.toString(response.getEntity());
            System.out.println("got result="+result);
            ObjectMapper mapper = new ObjectMapper();
            LCSErrorResponse lcsErrorResponse = mapper.readValue(result, LCSErrorResponse.class);
            Assert.assertEquals("Invalid JSON",lcsErrorResponse.getMessage());
            client.close();
        } catch (Exception e) {
            System.out.println("Exception encountered: "+e.getMessage());
            Assert.assertTrue(false);// error happened
        }
    }

    @DisplayName("NOT JSON status NOT OK")
    @Test
    public void testNotJSON_ErrorResponse_NOT_OK() {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/lcs");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        try {
            String json = "adfkja;rdflgjakdfg";//"{\"goobage\": [{\"value\": \"comcast\"},{\"value\": \"comcast\"},{\"value\": \"comcast\"}]}";
            HttpEntity httpEntity = new SerializableEntity(json);
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = client.execute(httpPost);
            Assert.assertEquals(400, response.getStatusLine().getStatusCode());
            String result = EntityUtils.toString(response.getEntity());
            System.out.println("got bad result=" + result);
            ObjectMapper mapper = new ObjectMapper();
            GenericException genericException = mapper.readValue(result, GenericException.class);
            Assert.assertEquals("Malformed JSON request", genericException.getMessage());
            client.close();
        } catch (JsonParseException e) {
            e.printStackTrace();
            Assert.assertEquals("Bad Request",e.getMessage());
            Assert.assertTrue(false);// error happened
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception encountered: "+e.getMessage());
            Assert.assertTrue(false);// error happened
        }
    }
}
