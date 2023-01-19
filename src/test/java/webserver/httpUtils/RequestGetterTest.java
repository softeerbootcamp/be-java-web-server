package webserver.httpUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import webserver.httpUtils.Request;
import webserver.httpUtils.RequestGetter;

public class RequestGetterTest {

    @Test
    public void testGetFromInputStream() throws IOException {
        String input = "GET /path?key=value HTTP/1.1\r\n" +
                "Host: example.com\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                "body=test";
        RequestGetter requestGetter = new RequestGetter();
        Request request = requestGetter.getFromInputStream(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        assertNotNull(request);
        assertEquals("GET", request.getReqLine().getMethod());
        assertEquals("/path?key=value", request.getReqLine().getQuery());
        assertEquals("HTTP/1.1", request.getReqLine().getVersion());

        Map<String, String> expectedHeaders = new HashMap<>();

        expectedHeaders.put("Host", "example.com");
        expectedHeaders.put("Content-Type", "application/json");
        assertEquals(expectedHeaders, request.getReqHeader().getAll());
        assertEquals("body=test", new String(request.getReqBody().getContext()));
    }
}
