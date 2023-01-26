package session;

public class HttpCookie {
    private String sid;

    public HttpCookie(String sid){
        this.sid = sid;

    }

    public String getSid() {
        return sid;
    }
    public void cleanCookie(){
        this.sid = null;
    }
    public boolean isLogin(){
        if(sid==null) {
            return false;
        }
        return true;

    }
}
