package request;

public class RequestLine {
    private String METHOD;

    private String URL;
    private String VERSION;
    private boolean userRequest;
    private static final int METHOD_TOKEN_INDEX = 0;
    private static final int URL_TOKEN_INDEX = 1;
    private static final int VERSION_TOKEN_INDEX = 2;

    public RequestLine(String requestLineFromRequest) {
        String[] filePath = requestLineFromRequest.split(" ");
        this.METHOD = filePath[METHOD_TOKEN_INDEX];
        this.URL = filePath[URL_TOKEN_INDEX];
        this.VERSION = filePath[VERSION_TOKEN_INDEX];
        this.userRequest = isUserRequest(this.URL);
    }
    public boolean getUserRequest(){
        return userRequest;
    }
    private boolean isUserRequest(String url){
        if(url.contains("/create")){
            return true;
        }else return false;
    }
    public String getURL() {
        return URL;
    }

    public String getMETHOD() {
        return METHOD;
    }

    public String getVERSION() {
        return VERSION;
    }
}
