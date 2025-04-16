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
        String t = fileService.fetchFile("QA.md");
        slotPointerHolder.setQuestions(t);
        log.info("=== 问题数据加载完成 ===");
        log.info("=== 业务数据初始化完成 ===");

    }
}
