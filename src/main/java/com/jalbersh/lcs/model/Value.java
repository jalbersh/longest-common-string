package com.jalbersh.lcs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class Value implements Serializable, Comparable<Value> {
    @JsonProperty("value")
    private String value;

    public Value() {}

    public Value(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Value{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public int compareTo(Value o) {
        return String.CASE_INSENSITIVE_ORDER.compare(getValue(),o.getValue());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value1 = (Value) o;
        return Objects.equals(value, value1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public boolean contains(String input) {
        return value.contains(input);
    }

}
