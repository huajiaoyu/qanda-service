package com.hjy.qanda.model;// Question.java
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @JsonProperty("qId")
    private String qId;
    private String question;
    private String answer;
}