package com.hjy.qanda.model;// MarkRequest.java
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MarkRequest {
    private String slotId;
    @JsonProperty("qId")
    private String qId;
}