package webserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.exception.HttpRequestException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ArgumentResolverTest {

    ArgumentResolver argumentResolver;

    @BeforeEach
    void testSetUp(){
        argumentResolver = new ArgumentResolver();
    }

    @Test
    @DisplayName("올바른 Argument가 들어왔을 떄")
    public void userCreateTest_validInput() throws HttpRequestException {

        //given
        Map<String, String> queryStrs = new HashMap<>();
        queryStrs.put("userId", "testUser");
        queryStrs.put("password", "testPass");
        queryStrs.put("name", "testName");
        queryStrs.put("email", "test@email.com");
        List<String> listStr = List.of("userId", "password", "name", "email");

        //when
        Map<String, String> resultMap = ArgumentResolver.checkParameters(queryStrs, listStr);

        //then
        Assertions.assertEquals(resultMap.size(),4);
    }

    @Test
    @DisplayName("올바르지 않은 Argument가 들어왔을 떄")
    public void userCreateTest_invalidInput() throws HttpRequestException {

        //given
        Map<String, String> queryStrs = new HashMap<>();
        queryStrs.put("test", "test@email.com");
        List<String> listStr = List.of("userId", "password", "name", "email");

        //then
        Assertions.assertThrows(HttpRequestException.class,()->ArgumentResolver.checkParameters(queryStrs, listStr));
    }
}