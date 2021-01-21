package com.jalbersh.lcs.logging;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskingPatternLayout extends PatternLayout {

    private final List<Pattern> maskPatterns = new ArrayList<>();
    private char mask;

    // Used by logback when confronted with multiple instances of the same xml tag
    @SuppressWarnings("unused")
    public void addMaskPattern(String pattern) {
        this.maskPatterns.add(Pattern.compile(pattern));
    }

    // Used by logback to set the mask variable
    @SuppressWarnings("unused")
    public void setMask(String mask) {
        this.mask = mask.charAt(0);
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        StringBuilder message = new StringBuilder(super.doLayout(event));

        for (Pattern pattern : maskPatterns) {
            Matcher matcher = pattern.matcher(message);
            while (matcher.find()) {
                for (int group = 0; group <= matcher.groupCount(); group++) {
                    if (matcher.group(group) != null) {
                        for (int j = matcher.start(group); j < matcher.end(group); j++) {
                            message.setCharAt(j, mask);
                        }
                    }
                }
            }
        }

        return message.toString();
    }
}
