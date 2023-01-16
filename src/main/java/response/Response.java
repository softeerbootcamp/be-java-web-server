package response;

public class Response {
    private byte[] file;
    private String data;

    private int code;

    private Response(byte[] file, String data, int code) {
        this.file = file;
        this.data = data;
        this.code = code;
    }

    private Response(String data, int code) {
        this.file = null;
        this.data = data;
        this.code = code;
    }

    public static Response of(byte[] file, String data, int code) {
        return new Response(file, data, code);
    }

    public static Response of(String data, int code) {
        return new Response(data, code);
    }

    public byte[] getFile() {
        return file;
    }

    public String getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Response{" +
                "data='" + data + '\'' +
                '}';
    }
}
