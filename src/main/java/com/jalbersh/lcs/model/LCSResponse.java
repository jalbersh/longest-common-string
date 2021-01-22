package com.jalbersh.lcs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*
{
"lcs": [
{"value": "cast"}
]
} */
public class LCSResponse {
    public LCSResponse() {values = new HashSet<>();
    }

    @JsonProperty("lcs")
    private Set<Value> values;

    public Set<Value> getValues() {
        return values;
    }

    public void setValues(Set<Value> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LCSResponse that = (LCSResponse) o;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }
}
