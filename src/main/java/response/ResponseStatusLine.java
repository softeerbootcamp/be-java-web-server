package response;

import enums.ControllerTypeEnum;
import enums.StatusCodeWithMessageEnum;

import static enums.ControllerTypeEnum.*;

public class ResponseStatusLine {
    private String protocol;
    private String statusCodeWithMessage;

    public ResponseStatusLine(ControllerTypeEnum controllerTypeEnum) {
        this.protocol = "HTTP/1.1";
        this.statusCodeWithMessage =
                StatusCodeWithMessageEnum.CODE_404.getKey() + " " + StatusCodeWithMessageEnum.CODE_404.getValue();
        if (controllerTypeEnum == STATIC) {
            this.statusCodeWithMessage =
                    StatusCodeWithMessageEnum.CODE_200.getKey() + " " + StatusCodeWithMessageEnum.CODE_200.getValue();
        }
        if (controllerTypeEnum == TEMPLATE) {
            this.statusCodeWithMessage =
                    StatusCodeWithMessageEnum.CODE_200.getKey() + " " + StatusCodeWithMessageEnum.CODE_200.getValue();
        }
        if (controllerTypeEnum == USER) {
            this.statusCodeWithMessage =
                    StatusCodeWithMessageEnum.CODE_302.getKey() + " " + StatusCodeWithMessageEnum.CODE_302.getValue();
        }
    }
    public String getResponseStatusLine(){
        return protocol+" "+statusCodeWithMessage;
    }


}
