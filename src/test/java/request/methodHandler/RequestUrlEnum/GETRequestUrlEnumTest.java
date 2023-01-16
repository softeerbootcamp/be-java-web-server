package request.methodHandler.RequestUrlEnum;

import model.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

public class GETRequestUrlEnumTest {
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

        Assertions.assertThat(list).isEqualTo(GETRequestUrlEnum.STATIC.getSupportingUrl(GETRequestUrlEnum.STATIC));
    }

    @Test
    @DisplayName("FORM handler test")
    public void FormHandleTest() {

    }
}
