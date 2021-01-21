package com.jalbersh.lcs.service;

import com.jalbersh.lcs.model.LCSRequest;
import com.jalbersh.lcs.model.LCSResponse;
import com.jalbersh.lcs.utils.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LCSService {
    private static final Logger logger = LoggerFactory.getLogger(LCSService.class);

    public LCSService() {
    }

    public Properties getProperties(String app) {
        Properties props = null;
        props = AppProperties.INSTANCE.getProperties();
        return props;
    }

    public String findLCS(String arr[])
    {
        int n = arr.length; // size of array
        String s = arr[0]; // first word to start
        int len = s.length(); // first word length
        String result = ""; // keep track of parts
        for (int i = 0; i < len; i++) { // letters in first word
            for (int j = i + 1; j <= len; j++) { // compare to parts in arrays
                String part = s.substring(i, j); // finds substrings
                int k = 1;
                for (k = 1; k < n; k++)
                    if (!arr[k].contains(part)) // compare base to substrings
                        break;
                if (k == n && result.length() < part.length()) // if found substring of greater length, keep it.
                    result = part;
            }
        }
        return result;
    }

    public LCSResponse process(LCSRequest request) {
        String[] arr = new String[request.getItems().size()];
        AtomicInteger index = new AtomicInteger();
        request.getItems().forEach(s -> {
            arr[index.getAndIncrement()] = s;
        });
        String response = findLCS(arr);
        Set<String> set = new HashSet<String>();
        set.add(response);
        LCSResponse lcsResponse = new LCSResponse();
        lcsResponse.setValues(set);
        return lcsResponse;
    }
}
