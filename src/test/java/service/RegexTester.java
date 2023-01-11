package service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexTester {
    @Test
    void test1(){
        String regex = "(/user/create).*";
        assertTrue("/user/create?qwerqadxvqwer".matches(regex));
    }
}