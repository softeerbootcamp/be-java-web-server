# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.


## 공부내용

## try-with-resources
try에 자원 객체를 전달하면, try 코드 블록이 끝나면 자동으로 자원을 종료해주는 기능이다.
즉, 따로 finally 블록이나 모든 catch 블록에 종료 처리를 하지 않아도 된다.
### close()가 호출되는 객체는?
try-with-resources가 모든 객체의 close()를 호출해주지는 않는다. AutoCloseable을 구현한 객체만 close()가 호출된다. 

## Socket이란?
소켓(Socket)은 TCP/IP 기반 네트워크 통신에서 데이터 송수신의 마지막 접점이다. 소켓 통신은 이러한 소켓을 이용해
서버-클라이언트간 데이터를 주고받는 양방향 연결 지향성 통신이다. 

### socket 통신
1. 서버 소켓을 생성해서 클라이언트의 요청을 받기 위한 준비를 한다.
```
int port = 8080;
ServerSocket listenSocket = new ServerSocket(port);
```
2. 클라이언트 접속 대기한다.
listenSocket.accept()를 이용해 클라이언트가 접속할 때까지 대기한다.
```
Socket connection;
while ((connection = listenSocket.accept()) != null {
    
}
```
accept() 메서드의 주석을 확인해보면 connection이 만들어질 때까지 즉, client에서 접속할 때까지 해당 스레드는 block 상태(대기)에 있는다고 나와있다.

```java
/*
 * Listens for a connection to be made to this socket and accepts
 * it. The method blocks until a connection is made.
 */
```
3. connection이 이루어진 후  while문을 실행하면서 새로운 스레드를 만든 후 connection을 넘겨 요청 처리를 한다. 
```
while ((connection = listenSocket.accept()) != null) {
	
	Thread thread = new Thread(new RequestHandler(connection));
	System.out.println(thread.getId() + " 스레드 생성");
	thread.start(); 
	}
```

### 소켓의 포트 번호
UDP의 경우 transport layer에서 dst port확인 후에 그에 상응하는 socket에 segment를 전달해 준다. 하지만 
TCP의 경우 4가지 identifier로 구분된다. (src IP Address, src Port, dst IP Address, dst Port)
