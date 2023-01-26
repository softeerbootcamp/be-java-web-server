package request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.RequestStartLine;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestStartLineTest {

    String startLineWithQuery;
    String startLine;

    @BeforeEach
    void setUp() {
        startLine ="GET /index.html HTTP/1.1";
        startLineWithQuery = "GET /user/create?userId=user&password=66a41ad2-bda9-4c70-807c-e0e13ff04475&name=1234&email=1234%40khu.ac.kr HTTP/1.1 ";
    }

    @Test
    @DisplayName("쿼리문이 없을 때 문자열로부터 RequestStartLine 객체가 제대로 생성되는지 테스트")
    void StartLinefromNoQuery() {
        RequestStartLine actualLine= RequestStartLine.from(startLine);
        assertThat(actualLine.getMethod().toString()).isEqualTo("GET");
        assertEquals("/index.html",actualLine.getUrl());
        assertEquals(true,actualLine.getQueries().isEmpty());
    }

    @Test
    @DisplayName("쿼리문이 있을 때 문자열로부터 RequestStartLine 객체가 제대로 생성되는지 테스트")
    void StartLinefromQuery() {
        RequestStartLine actualLine= RequestStartLine.from(startLineWithQuery);
        assertThat(actualLine.getMethod().toString()).isEqualTo("GET");
        assertEquals("/user/create",actualLine.getUrl());
        assertEquals("user",actualLine.getQueries().get("userId"));
        assertEquals("1234",actualLine.getQueries().get("name"));
    }

    @Test
    @DisplayName("일반 쿼리 스트링 문자열으로부터 Map에 쿼리 정보 저장하기 테스트")
    void getQuriesFromStr() {
        Map<String,String> actualQueryMap = RequestStartLine.getQuriesFromStr(startLineWithQuery);
        assertThat(actualQueryMap.entrySet().size()).isEqualTo(4);
        assertThat(actualQueryMap.get("name")).isEqualTo("1234");
    }

}
