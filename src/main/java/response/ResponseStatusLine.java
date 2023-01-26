package response;

import enums.ControllerTypeEnum;
import enums.HttpVersionTypeEnum;
import enums.StatusCodeWithMessageEnum;

import static enums.ControllerTypeEnum.*;

public class ResponseStatusLine {
    private String protocol;
    private String statusCodeWithMessage;
    private static final String NEW_LINE = "\r\n";

    public ResponseStatusLine(ControllerTypeEnum controllerTypeEnum) {
        // todo : 이거 request에서 받은 버전을 response로 보내줘야 하는 것으로 알고 있읍니당
        this.protocol = HttpVersionTypeEnum.HTTP1_1.getValue();
        this.statusCodeWithMessage =
                StatusCodeWithMessageEnum.CODE_404.getKey() + " " + StatusCodeWithMessageEnum.CODE_404.getValue();
        if(controllerTypeEnum==REDIRECT){
            this.statusCodeWithMessage =
                    StatusCodeWithMessageEnum.CODE_302.getKey() + " " + StatusCodeWithMessageEnum.CODE_404.getValue();
        }
        if (controllerTypeEnum == STATIC) {
            this.statusCodeWithMessage =
                    StatusCodeWithMessageEnum.CODE_200.getKey() + " " + StatusCodeWithMessageEnum.CODE_200.getValue();
        }
        if (controllerTypeEnum == TEMPLATE) {
            this.statusCodeWithMessage =
                    StatusCodeWithMessageEnum.CODE_200.getKey() + " " + StatusCodeWithMessageEnum.CODE_200.getValue();
        }
        if (controllerTypeEnum == USER || controllerTypeEnum == LOGIN) {
            this.statusCodeWithMessage =
                    StatusCodeWithMessageEnum.CODE_302.getKey() + " " + StatusCodeWithMessageEnum.CODE_302.getValue();
        }
    }

    public String getResponseStatusLineInString() {
        return protocol + " " + statusCodeWithMessage;
    }

    public String getStatusCodeWithMessage() {
        return statusCodeWithMessage;
    }
}
