package response;

import java.io.DataOutputStream;

public class Data {
    private DataOutputStream clientOutputStream;
    private byte[] data;

    public Data(DataOutputStream clientOutputStream, byte[] data) {
        this.clientOutputStream = clientOutputStream;
        this.data = data;
    }

    public Data(DataOutputStream clientOutputStream) {
        this.clientOutputStream = clientOutputStream;
        this.data = new byte[0];
    }


    public DataOutputStream getClientOutputStream() {
        return clientOutputStream;
    }

    public byte[] getData() {
        return data;
    }
}
