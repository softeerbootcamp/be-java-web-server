package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileIoUtilsTest {

    @Test
    @DisplayName("경로에 해당되는 파일이 없을 때")
    void loadNoFileFromClasspath() {
        //given
        String path ="./templates/user/users";

        //when, then
        assertThrows(NullPointerException.class,()->{
            FileIoUtils.loadFileFromClasspath(path);
        });
    }
}
