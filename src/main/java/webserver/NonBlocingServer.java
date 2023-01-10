package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NonBlocingServer {

    private Selector selector = null;
    private List<SelectionKey> room = new ArrayList<>();
    private static SocketChannel socketChannel = null;

    public static void main(String[] args) {

        /* TODO
         *   구현 테스트 중이므로 완성된 문서가 아님
         *   코드 리뷰에는 포함되지 않아도 되는 파일입니다.
         * */

        // SocketChannel을 생성하고, 몇 가지 설정을 한다.
//        socketChannel = SocketChannel.open();
//        socketChannel.configureBlocking(true);
//
//        // 서버에 연결하기.
//        socketChannel.connect(new InetSocketChannel("localhost", 10000));
//
//        // 마찬가지로, 연결된 서버와 입/출력해보기.
//        Charset charset = Charset.forName("UTF-8");
//
//        ByteBuffer byteBuffer = charset.encode("Hello, Server !");
//        socketChannel.write(byteBuffer);
//        System.out.println("Sending Success");
//
//        byteBuffer = ByteBuffer.allocate(128);
//        socketChannel.read(byteBuffer);
//        System.out.println("Received : " + charset.decode(byteBuffer).toString());
//
//        // 서버와 볼일이 끝났으면, 소켓 닫기.
//        if (socketChannel.isOpen()) {
//            socketChannel.close();
//        }
//    }
//
//    public void initServer() throws IOException {
//        selector = Selector.open();
//        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//        serverSocketChannel.configureBlocking(false);
//        serverSocketChannel.bind(new InetSocketAddress(10000));
//
//        // 채널에 Selector를 등록한다.
//        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//    }
//
//    public void startServer() throws Exception {
//        while (true) {
//            // 발생한 이벤트가 있는지 확인한다.
//            selector.select();
//
//            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
//
//            for (SelectionKey key : selectionKeySet) {
//                if (key.isAcceptable()) {
//                    accept(key);
//                }
//                else if (key.isReadable()) {
//                    read(key);
//                }
//            }
//        }
//    }
//
//    private void accept(SelectionKey key) throws Exception {
//        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
//
//        SocketChannel socketChannel = serverSocketChannel.accept();
//
//        if (null == socketChannel) {
//            return;
//        }
//
//        socketChannel.configureBlocking(false);
//        socketChannel.register(selector, SelectionKey.OP_READ);
//
//        room.add(socketChannel); // 연결된 클라이언트 추가하기.
//        System.out.println(socketChannel.toString() + "클라이언트가 접속했습니다.");
//    }
//
//    private void read(SelectionKey key) {
//        Charset charset = Charset.forName("UTF-8");
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024); // buffer 생성
//
//        try (SocketChannel socketChannel = (SocketChannel) key.channel()) {
//            socketChannel.read(byteBuffer); // 클라이언트 소켓으로부터 데이터를 읽음
//        } catch (IOException ex) {
//            room.remove(socketChannel);
//            logger.error(ex.getMessage());
//        }
//
//        System.out.println("Received Data : " + charset.decode(byteBuffer).toString());
//        byteBuffer.clear();
//    }
    }
}
