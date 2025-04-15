package com.hjy.qanda.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class FileUtil {

    public static String readFileStr(String filePath){
        Path path = Path.of(filePath);
        if(Files.notExists(path)){
            return "";
        }
        try {
            return Files.readString(path);
        } catch (IOException e) {
            log.info("读取文件{}失败", filePath);
            throw new RuntimeException(e);
        }
    }

    public static List<String> readFileStrLines(String filePath){
        Path path = Path.of(filePath);
        if(Files.notExists(path)){
            return Collections.emptyList();
        }
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            log.info("读取文件{}失败", filePath);
            throw new RuntimeException(e);
        }
    }
}
