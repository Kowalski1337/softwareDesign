package com.ifmo.vbaydyuk.response.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {
    @JsonProperty(value = "error_code", required = true)
    public
    int error_code;
    @JsonProperty(value = "error_msg", required = true)
    public
    String error_msg;

    public Error(
            @JsonProperty(value = "error_code", required = true) int error_code,
            @JsonProperty(value = "error_msg", required = true) String error_msg) {
        this.error_code = error_code;
        this.error_msg = error_msg;
    }

    @Override
    public String toString() {
        return "Error{" +
                "errorCode=" + error_code +
                ", errorMessage='" + error_msg + '\'' +
                '}';
    }
}