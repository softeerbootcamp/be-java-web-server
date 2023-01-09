package reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpMethod;
import util.error.HttpsErrorMessage;

import java.net.ProtocolException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestReaderTest {



    @Test
    @DisplayName("HttpMethod enum으로 method에 맞는 Reader가져오기(GET)")
    void selectRequestReaderByMethod() throws ProtocolException {
        RequestReader requestReader = RequestReader.selectRequestReaderByMethod(HttpMethod.GET);
        assertThat(requestReader).isInstanceOf(RequestGetReader.class);
    }

    @Test
    @DisplayName("HttpMethod enum으로 method에 맞는 Reader가져오기(NOT_MATCH)")
    void selectRequestReaderByMethodError() throws ProtocolException {
        ProtocolException protocolException = assertThrows(ProtocolException.class, () -> RequestReader.selectRequestReaderByMethod(HttpMethod.NOT_MATCH));
        assertThat(protocolException.getMessage()).isEqualTo(HttpsErrorMessage.NOT_VALID_HTTP_FORMAT);
    }
}