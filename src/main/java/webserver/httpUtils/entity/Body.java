package webserver.httpUtils.entity;

public class Body{
    byte[] context;

    public byte[] getContext() {
        return context;
    }

    public void setContext(byte[] context) {
        this.context = context;
    }

    public void setContext(String context){this.context = context.getBytes();}
}
