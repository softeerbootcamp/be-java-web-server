package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    @Test
    void timeTest(){
        Session sess = new Session("1234", "1234");

        System.out.println(sess.toString());
    }

}