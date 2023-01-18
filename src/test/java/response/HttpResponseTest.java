package response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

//    @DisplayName("200상태의 HttpResponse 생성")
//    @Test
//    void of() throws IOException, URISyntaxException {
//        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
//
//        Map<String, String > headerFields = new HashMap<>();
//        headerFields.put("Content-Type", "text/html;charset=utf-8");
//        headerFields.put("Content-Length", String.valueOf(body.length));
//        Header header = new Header(headerFields);
//        HttpResponse httpResponse = HttpResponse.of("200", header, body);
//
//        assertThat(httpResponse.getStatusLine().getValue()).isEqualTo("HTTP/1.1 200 OK");
//        assertThat(httpResponse.getHeader()).contains("Content-Type: text/html;charset=utf-8",
//                "Content-Length: 6902");
//        assertThat(httpResponse.getBody()).isEqualTo(body);
//    }

}
