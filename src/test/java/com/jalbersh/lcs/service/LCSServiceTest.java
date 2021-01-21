package com.jalbersh.lcs.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        String result = lcsService.findLCS(arr);
        Assert.assertEquals("grace",result);
     }

    @Test
    public void testFindLCS_returns_no_substring() {
        String arr[] = { "grace", "faithful", "disrespectful","beautifully","disgrace" };
        String result = lcsService.findLCS(arr);
        Assert.assertEquals("",result);
    }

//    @Test
    public void testFindLCS_returns_multiple_substrings() {
        String arr[] = { "gracebaser", "gracefulbaser", "disgracefulbaser","gracefullybaser","disgracebaser" };
        String result = lcsService.findLCS(arr);
        Assert.assertEquals("grace",result);
    }

}