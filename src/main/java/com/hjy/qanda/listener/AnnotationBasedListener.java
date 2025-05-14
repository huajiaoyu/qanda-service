package com.hjy.qanda.listener;

import com.hjy.qanda.service.FileService;
import com.hjy.qanda.service.SlotPointerHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class AnnotationBasedListener {

    @Autowired
    private FileService fileService;

    @Autowired
    private SlotPointerHolder slotPointerHolder;

    @EventListener
    public void handleStartedEvent(ApplicationStartedEvent event) {
        log.info("=== 初始化业务数据 ===");
        log.info("=== 加载问题数据 ===");
        String t = fileService.fetchFile("QA1.md");
        slotPointerHolder.setQuestions(0,t);
        t = fileService.fetchFile("QA1-S.md");
        slotPointerHolder.setQuestions(1,t);
        t = fileService.fetchFile("QA0-gailun.md");
        slotPointerHolder.setQuestions(2,t);
        t = fileService.fetchFile("QA2-peizhibiangeng.md");
        slotPointerHolder.setQuestions(3,t);
        t = fileService.fetchFile("QA3-jixiaouyu-mini.md");
        slotPointerHolder.setQuestions(4,t);
        t = fileService.fetchFile("info.md");
        slotPointerHolder.setQuestions(5,t);
        log.info("=== 问题数据加载完成 ===");
        log.info("=== 业务数据初始化完成 ===");

    }
}
