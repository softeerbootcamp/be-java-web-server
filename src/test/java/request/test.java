package request;

import org.junit.jupiter.api.Test;

public class test {

    @Test
    public void test(){
        String test = "Connection: keep-alive";

        String[] split = test.split(":\\s");

        for (String i : split) {
            System.out.println(i);
        }


    }
}
