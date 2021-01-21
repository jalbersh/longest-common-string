package com.jalbersh.lcs.service;

import com.jalbersh.lcs.model.LCSRequest;
import com.jalbersh.lcs.model.LCSResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LCSService {
    private static final Logger logger = LoggerFactory.getLogger(LCSService.class);

    public LCSService() {
    }

    public String findLCS(String arr[], Set<String> ignore)
    {
        ignore.forEach(s -> logger.info("ignoring "+s));
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
                    if (!ignore.contains(part)) result = part;
            }
        }
        return result;
    }

    private boolean add(Set<String> set, String input) {
        AtomicBoolean found = new AtomicBoolean(false);
        if (set.size() != 0) {
            set.forEach(s -> {
                if (s.contains(input) || s.equals(input)) found.set(true);
            });
        }
        if (!found.getPlain()) {
            set.add(input);
            return true;
        }
        return false;
    }

    public LCSResponse process(LCSRequest request) throws Exception {
        Set<String> ignore = new HashSet<>();
        int size = request.getItems().size();
        String[] arr = new String[size];
        Set<String> inset = new HashSet<String>();
        AtomicInteger index = new AtomicInteger();
        request.getItems().forEach(s -> {
            arr[index.getAndIncrement()] = s;
            inset.add(s);
        });
        System.out.println("insert size="+inset.size());
        if (inset.size() != size || size < 2) {
            throw new Exception("Input must be a set of unique strings");
        }
        String response = "";
        do {
            logger.info("calling findLCS");
            response = findLCS(arr, ignore);
            if (!response.isEmpty()) {
                logger.info("adding "+response);
                if (!add(ignore,response)) break;
            }
        } while (!response.isEmpty());
        logger.info("process ignore size="+ignore.size());
        Set<String> outset = new HashSet<>();
        ignore.forEach(s -> outset.add(s));
        LCSResponse lcsResponse = new LCSResponse();
        lcsResponse.setValues(outset);
        return lcsResponse;
    }
}
