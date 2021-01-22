package com.jalbersh.lcs.service;

import com.jalbersh.lcs.error.LCSSetException;
import com.jalbersh.lcs.model.LCSRequest;
import com.jalbersh.lcs.model.Value;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LCSServiceTest {
    private LCSService lcsService;

    @Before
    public void setUp() throws Exception {
        lcsService = new LCSService();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindLCS_returns_best_substring() {
        String arr[] = { "grace", "graceful", "disgraceful","gracefully","disgrace" };
        Set<String> ignore = new HashSet<>();
        String result = lcsService.findLCS(arr,ignore);
        Assert.assertEquals("grace",result);
     }

    @Test
    public void testFindLCS_returns_no_substring() {
        String arr[] = { "grace", "faithful", "disrespectful","beautifully","disgrace" };
        Set<String> ignore = new HashSet<>();
        String result = lcsService.findLCS(arr,ignore);
        Assert.assertEquals("",result);
    }

    @Test
    public void testFindLCS_returns_multiple_substrings() {
        String arr[] = { "gracebaser", "gracefulbaser", "disgracefulbaser","gracefullybaser","disgracebaser" };
        Set<String> ignore = new HashSet<>();
        String result = lcsService.findLCS(arr,ignore);
        Assert.assertEquals("grace",result);
        ignore.add("grace");
        result = lcsService.findLCS(arr,ignore);
        Assert.assertEquals("baser",result);
    }

    @Test
    public void testProcess_with_bad_input_returns_exception() {
        String arr[] = { "grace", "grace" };
        LCSRequest lcsRequest = new LCSRequest();
        Arrays.stream(arr).forEach(s -> lcsRequest.getStrings().add(new Value(s)));
        try {
            lcsService.process(lcsRequest);
        } catch (LCSSetException e) {
            Assert.assertEquals("Input must be a set of unique strings",e.getMessage());
        } catch (Exception e) {
            Assert.assertEquals("something else",e.getMessage());
        }
    }

}