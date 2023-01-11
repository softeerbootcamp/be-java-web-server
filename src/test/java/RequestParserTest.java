import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.RequestParser;

import java.io.*;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {

    String mockHeader = "Host: localhost:8080"+"";

    @Test
    void parseHeaderTest() throws IOException {
        InputStream inputFileStream = new ByteArrayInputStream(mockHeader.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(inputFileStream,"UTF-8"));
        Map<String,String> actualMap = RequestParser.parseHeader(br);
        System.out.println(actualMap.entrySet().size());
        //assertThat(actualMap.get("Host")).isEqualTo("localhost:8080");
    }
}
