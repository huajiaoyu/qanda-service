package com.hjy.qanda.controller;// QuestionController.java
import com.hjy.qanda.model.CheckNextQuestionRes;
import com.hjy.qanda.model.MarkRequest;
import com.hjy.qanda.model.ProcessResp;
import com.hjy.qanda.model.Question;
import com.hjy.qanda.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/list")
    public List<String> getQuestionsList() {
        return List.of("十大知识域", "管理概论", "配置变更", "绩效域","信息");
    }

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

    @GetMapping("/{slotId}/download/marks")
    public ResponseEntity<Resource> downloadMarks(@PathVariable String slotId) {

        String marksMd = questionService.getMarksMd(slotId);

        InputStream stream = new ByteArrayInputStream(marksMd.getBytes(StandardCharsets.UTF_8));
        InputStreamResource resource =new InputStreamResource(stream);

        // 设置文件名
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename("marks.md")
                        .build()
        );

        return ResponseEntity.ok().contentLength(3).headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }
}