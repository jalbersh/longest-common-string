package com.jalbersh.lcs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel
public class LCSRequest implements Serializable {
    public LCSRequest() {
        strings = new HashSet<>();
    }

    @ApiModelProperty(name = "setOfStrings", dataType = "Set", example = "[{\"value\": \"comcast\"},{\"value\": \"comcastic\"},{\"value\": \"broadcaster\"}]")
    @JsonProperty("setOfStrings")
    private Set<Value> strings;

    public Set<Value> getStrings() {
        return strings;
    }

    public void setStrings(Set<Value> strings) {
        this.strings = strings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LCSRequest that = (LCSRequest) o;
        return Objects.equals(strings, that.strings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strings);
    }
}
