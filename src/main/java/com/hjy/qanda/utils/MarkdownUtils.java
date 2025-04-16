package com.hjy.qanda.utils;

import com.hjy.qanda.model.Question;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MarkdownUtils {

    public static List<Question> getQuestionsFromMarkdown(String markdownStr) {
        AtomicInteger i = new AtomicInteger(1);
        return parseMarkdown(markdownStr).stream().map(e -> new Question(String.valueOf(i.getAndIncrement()), e.get("question"), e.get("answer"))).toList();
    }

    private static List<Map<String, String>> parseMarkdown(String markdown) {
        List<Map<String, String>> result = new ArrayList<>();
        String[] lines = markdown.split("\n");
        String currentH1 = null;
        String currentH2 = null;
        StringBuilder currentAnswer = new StringBuilder();

        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.startsWith("# ")) {
                // 处理一级标题
                String h1 = trimmedLine.substring(2).trim();
                if (currentH1 != null && currentH2 != null && currentAnswer.length() > 0) {
                    addEntry(result, currentH1, currentH2, currentAnswer.toString());
                }
                currentH1 = h1;
                currentH2 = null;
                currentAnswer.setLength(0);
            } else if (trimmedLine.startsWith("## ")) {
                // 处理二级标题
                String h2 = trimmedLine.substring(3).trim();
                if (currentH1 == null) {
                    continue; // 忽略没有一级标题的二级标题
                }
                if (currentH2 != null && currentAnswer.length() > 0) {
                    addEntry(result, currentH1, currentH2, currentAnswer.toString());
                }
                currentH2 = h2;
                currentAnswer.setLength(0);
            } else {
                // 处理正文内容
                if (currentH1 != null && currentH2 != null) {
                    if (currentAnswer.length() > 0) {
                        currentAnswer.append("\n");
                    }
                    currentAnswer.append(line);
                }
            }
        }

        // 处理最后一个条目
        if (currentH1 != null && currentH2 != null && currentAnswer.length() > 0) {
            addEntry(result, currentH1, currentH2, currentAnswer.toString());
        }

        return result;
    }

    private static void addEntry(List<Map<String, String>> list, String h1, String h2, String answer) {
        Map<String, String> entry = new LinkedHashMap<>();
        entry.put("question", h1 + "-" + h2);
        entry.put("answer", answer);
        list.add(entry);
    }

}
