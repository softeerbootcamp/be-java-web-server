package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;
import response.HttpResponse;
import utils.FileIoUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    @Test
    @DisplayName("해당 파일 내용이 잘 들어가는지 확인")
    void serveFile() {
        //given
        String url = "./templates/user/form.html";
        HttpResponse response = new HttpResponse();
        byte[] contents = FileIoUtils.loadFileFromClasspath(url);

        //when
        FileService.serveFile(url,response);
        byte[] body = response.getBody();

        //then
        assertThat(contents).isEqualTo(body);
    }
}
