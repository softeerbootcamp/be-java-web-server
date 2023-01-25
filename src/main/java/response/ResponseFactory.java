package response;

import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;

public class ResponseFactory {


    private ResponseStatusLine responseStatusLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;
    private static final String NEW_LINE = "\r\n";

    public ResponseFactory(Builder builder) {
        this.responseStatusLine = builder.responseStatusLine;
        this.responseHeader = builder.responseHeader;
        this.responseBody = builder.responseBody;
    }

    public ResponseStatusLine getResponseStatusLine() {
        return responseStatusLine;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    // 추가된 명령들을 response에 적기 위해 하나의 string 문장으로 형성

    public static class Builder {
        private ResponseStatusLine responseStatusLine;
        private ResponseHeader responseHeader;
        private ResponseBody responseBody;

        public Builder builder() {
            return this;
        }

        public Builder setResponseStatusLine(ControllerTypeEnum controllerTypeEnum) {
            this.responseStatusLine = new ResponseStatusLine(controllerTypeEnum);
            return this;
        }

        public Builder setResponseHeader(ContentTypeEnum contentTypeEnum, int lengthOfBodyContent) {
            this.responseHeader = new ResponseHeader(contentTypeEnum, lengthOfBodyContent);
            return this;
        }

        public Builder setResponseBody(byte[] body) {
            this.responseBody = new ResponseBody(body);
            return this;
        }

        public Builder addResponseHeader(String addedLine) {
            this.responseHeader.addHeaderLine(addedLine);
            return this;
        }

        public Builder addCookieHeader(String sid) {
            if(sid==null) {
                return this;
            }
            this.responseHeader.addCookieLine(sid);
            return this;
        }

        public ResponseFactory build() {
            return new ResponseFactory(this);
        }
    }

}
