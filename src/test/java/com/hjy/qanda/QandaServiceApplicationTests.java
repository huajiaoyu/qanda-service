package com.hjy.qanda;

import com.hjy.qanda.service.FileService;
import com.hjy.qanda.service.feign.IObjectClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QandaServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private FileService fileService;
    @Test
    void test() {
        fileService.fetchFile("QA.md");
    }

}
