package com.hjy.qanda.service;

import com.hjy.qanda.service.feign.IObjectClient;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class FileService {

    @Autowired
    private IObjectClient objectClient;

    public String fetchFile(String fileName){
        Response rsp = objectClient.getFile("QA.md");
        try(InputStream is = rsp.body().asInputStream()) {
            byte[] bytes = IOUtils.toByteArray(is);
            return new String(bytes);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return "";
    }
}
