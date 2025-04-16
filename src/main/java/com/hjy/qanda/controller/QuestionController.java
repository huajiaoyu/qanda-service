package com.hjy.qanda.controller;// QuestionController.java
import com.hjy.qanda.model.CheckNextQuestionRes;
import com.hjy.qanda.model.MarkRequest;
import com.hjy.qanda.model.ProcessResp;
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
    @GetMapping("/{slotId}/check")
    public CheckNextQuestionRes checkNextQuestion(@PathVariable String slotId) {
        return questionService.check(slotId);
    }

    // GET /api/v1/question/{slotId}/next
    @GetMapping("/{slotId}/next")
    public Question getNextQuestion(@PathVariable String slotId) {
        Question question = questionService.getNext(slotId);
        return question;
    }

    // GET /api/v1/question/{slotId}/next
    @GetMapping("/{slotId}/current")
    public Question getCurrentQuestion(@PathVariable String slotId) {
        Question question = questionService.getCur(slotId);
        return question;
    }

    // GET /api/v1/question/{slotId}/next
    @GetMapping("/{slotId}/process")
    public ProcessResp getProcess(@PathVariable String slotId) {
        ProcessResp rsp = questionService.getProc(slotId);
        return rsp;
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