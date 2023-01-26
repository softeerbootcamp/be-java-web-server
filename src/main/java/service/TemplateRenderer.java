package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TemplateRenderer {
    /*
    1. 템플릿 읽기
    2. 주어진 태그 위치 찾기
    3. 위치에 쓰기
     */
    private static final Logger logger = LoggerFactory.getLogger(TemplateRenderer.class);
    private final StringBuilder template;

    public TemplateRenderer(String templatePath) throws IOException {
        this.template = new StringBuilder();

        try {
            String curLine = "";
            BufferedReader br = new BufferedReader(new FileReader(templatePath));

            while ((curLine = br.readLine()) != null) {
                template.append(curLine);
            }
        } catch(IOException e) {
            logger.error("cannot open file {}", e.getMessage());
            throw new IOException(e);
        }
    }

    public StringBuilder replaceStringWithGivenString(String toReplace, String given) throws IOException {
        int startIdx = template.indexOf(toReplace);
        this.template.replace(startIdx, startIdx+toReplace.length(), "");
        this.template.insert(startIdx, given);

        return this.template;
    }

    @Override
    public String toString() {
        return template.toString();
    }
}
