package com.hjy.qanda.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class QuestionsFileProp {

    public enum FileType {
        remote, local
    }

    @Value("${qanda.file.type:local}")
    private FileType type;

    @Value("${qanda.file.path.prefix:QA.md}")
    private String pathPrefix;

}
