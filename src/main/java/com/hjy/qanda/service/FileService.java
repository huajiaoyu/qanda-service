package com.hjy.qanda.service;

import com.hjy.qanda.config.QuestionsFileProp;
import com.hjy.qanda.service.feign.IObjectClient;
import com.hjy.qanda.utils.FileUtil;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class FileService {

    @Autowired
    private IObjectClient objectClient;

    @Autowired
    public QuestionsFileProp prop;

    public String fetchFile(String fileName) {
        switch (prop.getType()) {
            case remote -> {
                Response rsp = objectClient.getFile("QA.md");
                try (InputStream is = rsp.body().asInputStream()) {
                    byte[] bytes = IOUtils.toByteArray(is);
                    return new String(bytes);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            case local -> {
                return FileUtil.readFileStr(prop.getName());
            }
            default -> {
                return "";
            }
        }
        throw new RuntimeException("无文件");
//        return FileUtil.readFileStr("D:\\github\\RuankaoGaoxiang\\notes\\绩效域.md");
    }
}
