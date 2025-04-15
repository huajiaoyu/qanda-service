package com.hjy.qanda.controller;// QuestionController.java
import com.hjy.qanda.model.CheckNextQuestionRes;
import com.hjy.qanda.model.MarkRequest;
import com.hjy.qanda.model.Question;
import com.hjy.qanda.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // GET /api/v1/question/{slotId}/next/check
    @GetMapping("/{slotId}/next/check")
    public CheckNextQuestionRes checkNextQuestion(@PathVariable String slotId) {
        return questionService.checkNext(slotId);
    }

    // GET /api/v1/question/{slotId}/next
    @GetMapping("/{slotId}/next")
    public Question getNextQuestion(@PathVariable String slotId) {
        Question question = questionService.getNext(slotId);
        return question;
    }

    // POST /api/v1/question/mark
    @PostMapping("/mark")
    public ResponseEntity<?> markQuestion(@RequestBody MarkRequest request) {
        questionService.markQuestion(request.getSlotId(), request.getQId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{slotId}/refresh")
    public ResponseEntity<?> markQuestion(@PathVariable String slotId) {
        questionService.refreshSlot(slotId);
        return ResponseEntity.ok().build();
    }
}