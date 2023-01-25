package request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestDataTypeTest {

    @Test
    @DisplayName("Pathvarailbe 타입 확인 테스트")
    void getUrlType() {
        //given
        String pathVariable = "/user/content/32";
        //when
        RequestDataType urlType = RequestDataType.getUrlType(pathVariable);
        //then
        assertThat(urlType).isEqualTo(RequestDataType.PATH_VARIABLE);

    }
}