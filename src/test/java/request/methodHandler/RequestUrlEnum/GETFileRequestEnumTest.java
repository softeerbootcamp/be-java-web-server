package request.methodHandler.RequestUrlEnum;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import request.method.GET.GETFileRequestEnum;

import java.util.ArrayList;
import java.util.List;

public class GETFileRequestEnumTest {
    @Test
    @DisplayName("GETRequestUrlEnum.getSupportingUrl test")
    public void getSupportingUrlTest() {
        List<String> list = new ArrayList<>();
        list.add(".css");
        list.add(".eot");
        list.add(".svg");
        list.add(".ttf");
        list.add(".woff");
        list.add(".woff2");
        list.add(".png");
        list.add(".js");

        Assertions.assertThat(list).isEqualTo(GETFileRequestEnum.STATIC.getSupportingFilePostfix(GETFileRequestEnum.STATIC));
    }

    @Test
    @DisplayName("FORM handler test")
    public void FormHandleTest() {

    }
}
