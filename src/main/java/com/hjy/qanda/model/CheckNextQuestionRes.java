package com.hjy.qanda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckNextQuestionRes {
    private String message;
    private boolean hasNext = true;
}
