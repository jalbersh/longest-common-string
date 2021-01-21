package com.jalbersh.lcs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.*;

/*
{
"setOfStrings": [
{"value": "comcast"},
{"value": "comcastic"},
{"value": "broadcaster"}
]
} */
public class LCSRequest implements Serializable {
    public LCSRequest() {
        items = new HashSet<>();
    }

    @JsonProperty("setOfStrings")
    private Set<String> items;

    public Set<String> getItems() {
        return items;
    }

    public void setItems(Set<String> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LCSRequest that = (LCSRequest) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
