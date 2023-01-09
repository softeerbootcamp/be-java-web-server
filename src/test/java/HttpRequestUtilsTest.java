import org.junit.jupiter.api.Test;
import util.HttpRequestUtils;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestUtilsTest {
    @Test
    public void getUrl(){
        String url = HttpRequestUtils.getUrl("GET /index.html HTTP/1.1");
        assertThat(url).isEqualTo("/index.html");
    }
}
