package webserver;

import java.net.Socket;

public class Client {
    final static String SERVER_IP = "127.0.0.1";
    final static int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
