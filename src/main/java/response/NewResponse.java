package response;

import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;

import java.util.List;

public class NewResponse {


    private ResponseStatusLine responseStatusLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;
    private List<String> responseAdder;
    private static final String NEW_LINE = "\r\n";

    public NewResponse(Builder builder){

    }
    public String ResponseAssembler(){
        return responseStatusLine.getResponseStatusLine()+NEW_LINE+
                responseHeader.getHeaderLine()+NEW_LINE+
                ResponseAdderListToString()+
                responseBody.getBody()+responseBody.getBody().length+NEW_LINE;
    }
    // 추가된 명령들을 response에 적기 위해 하나의 string 문장으로 형성
    public String ResponseAdderListToString(){
        String result="";
        for(String line : responseAdder){
            result+=(line+NEW_LINE);
        }
        return result;
    }
    public static class Builder {
        private ResponseStatusLine responseStatusLine;
        private ResponseHeader responseHeader;
        private ResponseBody responseBody;
        private List<String> responseAdder;
        public Builder(){

        }

        public Builder setResponseStatusLine(ControllerTypeEnum controllerTypeEnum) {
            this.responseStatusLine = new ResponseStatusLine(controllerTypeEnum);
            return this;
        }

        public Builder setResponseHeader(ContentTypeEnum contentTypeEnum, int lengthOfBodyContent) {
            this.responseHeader = new ResponseHeader(contentTypeEnum,lengthOfBodyContent);
            return this;
        }

        public Builder setResponseBody(byte[] body) {
            this.responseBody = new ResponseBody(body);
            return this;
        }
        public Builder addResponseHeader(String addedLine) {
            this.responseAdder.add(addedLine);
            return this;
        }

        public NewResponse build(){
            return new NewResponse(this);
        }
    }

}
