# java-was-2022
Java Web Application Server 2022

## Socket Programming
![socket](https://t1.daumcdn.net/cfile/tistory/99BD563359E489460F)
#### ServerSocket 클래스
* 서버 프로그램에서 사용하는 소켓
* 포트를 통해 연결 요청이 오기를 대기
* 요청이 오면 클라이언트와 연결을 맺고 해당 클라이언트와 통신하는 새 소켓을 만드는 일을 한다.
* 새로 만들어진 소켓은 클라이언트 소켓과 데이터를 주고받는다.
* 자바 7에서 ServerSocket은 AutoCloseable 인터페이스를 구현하고 있으므로, try-with-resources 구문을 이용할 수 있다.
  * try-with-resources는 try(...) 문에서 선언된 객체들에 대해서 try가 종료될 때 자동으로 자원을 해제해주는 기능
  * AutoCloseable은 try에 선언된 객체가 AutoCloseable을 구현했더라면 Java는 try 구문이 종료될 때 객체의 close() 메소드를 자동으로 호출

#### Socket 클래스
* 서버 프로그램으로 연결 요청
* 데이터 전송을 담당

<pre>
<code>
public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 1. 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        // 생성자 내부에 bind()가 있고, bind() 내부에 listen() 있음
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            Socket connection;//클라이언트 소켓과의 연결 객체
            //2. 서버 소켓 listenSocket가 accpet를 호출하고 리턴을 해주면 소켓인 connection 객체가 생성됨
            //accept()가 호출되면 프로그램은 여기서 실행을 멈추고 클라이언트가 port번으로 연결할 때까지 무한 대기
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection));
                thread.start();
            }
        }
    }
</code>
</pre>

*** 
## HTTP 메시지 구조 및 핵심 내용
1. **정의 : HyperText Transfer Protocol** <br>
   하이퍼텍스트(HTML) 문서를 교환하기 위해 만들어진 protocol(통신 규약).<br>
   즉 웹상에서 네트워크로 서버끼리 통신을 할때 어떠한 형식으로 서로 통신을 하자고 규정해 놓은 "통신 형식" 혹은 "통신 구조" 라고 보면 된다.<br>
   HTTP는 TCP/IP 기반으로 되어있다.<br>
2. **HTTP 특징**<br>
   2-1. HTTP 기본적으로 요청/응답 (request/response) 구조로 되어있다.<br>
   클라이언트가 HTTP request를 서버에 보내면 서버는 HTTP response를 보내는 구조.<br>
   클라이언트와 서버의 모든 통신이 요청과 응답으로 이루어 진다.<br>
   2-2. HTTP는 Stateless 이다.<br>
   Stateless 란 말그대로 state(상태)를 저장하지 않는 다는 뜻.<br>
   즉 각각의 요청/응답은 독립적인 요청/응답 이다.
   예를 들어, 클라이언트가 요청을 보내고 응답을 받은후, 조금 있다 다시 요청을 보낼때, 전에 보낸 요청/응답에 대해 알지 못한다는 뜻이다.<br>
   **그래서 만일 여러 요청과응답 의 진행과정이나 데이터가 필요할때는 쿠키나 세션 등등을 사용하게 된다.**

### HTTP REQUEST MESSAGE 구조
![http_request_message](https://blog.kakaocdn.net/dn/bUk1MH/btqD9Nwa5bh/NDK8mt53eo7gqIcJlTSqI1/img.png)
#### Start Line
* 말 그대로 메세지의 start line
* 세 부분으로 나누어져 있음
* HTTP METHOD
  * 해당 request가 의도한 action을 정의
  * GET, POST, PUT, DELETE ... 등
* REQUEST TARGET
  * 해당 request가 전송되는 목표 uri
* HTTP VERSION
  * 말 그대로 사용되는 HTTP version
#### Headers
* 해당 request에 대한 추가 정보를 담고 있는 부분
* 자주 사용되는 header 정보에는 다음이 있다
  * Host <br>
    요청이 전송되는 target의 host url: 예를 들어, google.com
  * User-Agent <br>
    요청을 보내는 클라이언트의 대한 정보: 예를 들어, 웹브라우저에 대한 정보.
  * Accept<br>
    해당 요청이 받을 수 있는 응답(response) 타입.
  * Connection <br>
    해당 요청이 끝난후에 클라이언트와 서버가 계속해서 네트워크 컨넥션을 유지 할것인지 아니면 끊을것인지에 대해 지시하는 부분.
  * Content-Type<br>
    해당 요청이 보내는 메세지 body의 타입. 예를 들어, JSON을 보내면 application/json.
  * Content-Length <br>
    메세지 body의 길이.
#### Body
해당 reqeust의 실제 메세지/내용. Body가 없는 request도 많다. <br>
예를 들어, GET request들은 대부분 body가 없는 경우가 많음.

### HTTP RESPONSE MESSAGE 구조
![http_response_message](https://www3.ntu.edu.sg/home/ehchua/programming/webprogramming/images/HTTP_ResponseMessageExample.png)
Response도 request와 마찬가지로 크게 3부분으로 구성되어 있다.
#### Status line
* HTTP 버전
* Status code: 응답 상태를 나타내는 코드. 숫자로 되어 있는 코드. 예를 들어, 200
  * 200 OK
  * 301 Moved Permanently : 해당 URI가 다른 주소로 바뀌었을때 보내는 코드.
  * 400 Bad Request : 해당 요청이 잘못된 요청일 때, 예를 들어, 전화번호를 보내야 되는데 text가 보내졌을 때
  * 401 Unauthorized
  * 403 Forbidden : 유저가 해당 요청에 대한 권한이 없다는 뜻, 예를 들어, 오직 과금을 한 유저만 볼 수 있는 데이터를 요청 했을 때
  * 404 Not Found : 요청된 uri가 존재 하지 않는다는 뜻.
  * 500 Internal Server Error
* Status text: 응답 상태를 간략하게 설명해주는 부분. 예를 들어, "Not Found"
#### Headers
* Response의 headers와 동일하다.
* 다만 response에서만 사용되는 header 값들이 있다.
  * 예를 들어, User-Agent 대신에 Server 헤더가 사용된다.
#### Body
Response의 body와 일반적으로 동일하다.<br>
Request와 마찬가지로 모든 response가 body가 있지는 않다. 데이터를 전송할 필요가 없을경우 body가 비어있게 된다.

*** 

## Static Page VS Dynamic Page
### Static Pages
Web Server는 파일 경로 이름을 받아 경로와 일치하는 file contents를 반환한다.<br>
항상 동일한 페이지를 반환한다.<br>
Ex) image, html, css, javascript 파일과 같이 컴퓨터에 저장되어 있는 파일들
### Dynamic Pages
인자의 내용에 맞게 동적인 contents를 반환한다.<br>
즉, 웹 서버에 의해서 실행되는 프로그램을 통해서 만들어진 결과물이다 <br>

***

## 웹 서버 VS WAS
### 웹 서버
웹 서버란 HTTP 프로토콜을 기반으로 클라이언트가 웹 브라우저에서 어떠한 요청을 하면 그 요청을 받아 정적 컨텐츠를 제공하는 서버이다.<br>
정적 컨텐츠란 단순 HTML 문서, CSS, 이미지, 파일 등 즉시 응답 가능한 컨텐츠이다.<br>
이때 웹 서버가 정적 컨텐츠가 아닌 동적 컨텐츠를 요청받으면 WAS에게 해당 요청을 넘겨주고, WAS에서 처리한 결과를 클라이언트에게 전달하는 역할도 해준다. 

 ### WAS
WAS란 DB 조회 혹은 다양한 로직 처리를 요구하는 동적 컨텐츠를 제공하기 위해 만들어진 Application 서버이다.<br>
HTTP 프로토콜을 기반으로 사용자 컴퓨터나 장치에 애플리케이션을 수행해주는 미들웨어로서, 주로 데이터베이스 서버와 같이 수행된다. <br>
WAS는 JSP, Servlet 구동환경을 제공해주기 때문에 서블릿 컨테이너 혹은 웹 컨테이너로 불린다. <br>
이러한 WAS는 웹 서버의 기능들을 구조적으로 분리하여 처리하고자 하는 목적으로 제시되었다. <br>
* Servlet: WAS 위에서 돌아가는 Java Program <br>

*** 


## 웹 서버와 WAS를 분리하는 이유
* 기능을 분리하여 서버 부하 방지
    * WAS는 DB 조회나 다양한 로직을 처리하느라 바쁘기 때문에 단순한 정적 컨텐츠는 Web Server에서 빠르게 클라이언트에 제공하는 것이 좋다. 
      * WAS는 기본적으로 동적 컨텐츠를 제공하기 위해 존재하는 서버이다. 
      * 만약 정적 컨텐츠 요청까지 WAS가 처리한다면 정적 데이터 처리로 인해 부하가 커지게 되고, 동적 컨텐츠의 처리가 지연됨에 따라 수행 속도가 느려진다. 즉, 이로 인해 페이지 노출 시간이 늘어나게 될 것이다.
* 물리적으로 분리하여 보안 강화
  * SSL에 대한 암복호화 처리에 Web Server를 사용
* 여러 대의 WAS를 연결 가능 
  * Load Balancing을 위해서 Web Server를 사용 
  * fail over(장애 극복), fail back 처리에 유리 
  * 특히 대용량 웹 어플리케이션의 경우(여러 개의 서버 사용) Web Server와 WAS를 분리하여 무중단 운영을 위한 장애 극복에 쉽게 대응할 수 있다.
    * 예를 들어, 앞 단의 Web Server에서 오류가 발생한 WAS를 이용하지 못하도록 한 후 WAS를 재시작함으로써 사용자는 오류를 느끼지 못하고 이용할 수 있다.
* 여러 웹 어플리케이션 서비스 가능 
  * 예를 들어, 하나의 서버에서 PHP Application과 Java Application을 함께 사용하는 경우
* 기타 
  * 접근 허용 IP 관리, 2대 이상의 서버에서의 세션 관리 등도 Web Server에서 처리하면 효율적이다. 

> 즉, 자원 이용의 효율성 및 장애 극복, 배포 및 유지보수의 편의성 을 위해 Web Server와 WAS를 분리한다.
Web Server를 WAS 앞에 두고 필요한 WAS들을 Web Server에 플러그인 형태로 설정하면 더욱 효율적인 분산 처리가 가능하다.

*** 

## Web Service Architecture
![아키텍처이미지](https://gmlwjd9405.github.io/images/web/web-service-architecture.png)
1. Web Server는 웹 브라우저 클라이언트로부터 HTTP 요청을 받는다.
2. Web Server는 클라이언트의 요청(Request)을 WAS에 보낸다.
3. WAS는 관련된 Servlet을 메모리에 올린다.
4. WAS는 web.xml을 참조하여 해당 Servlet에 대한 Thread를 생성한다. (Thread Pool 이용)
5. HttpServletRequest와 HttpServletResponse 객체를 생성하여 Servlet에 전달한다. <br>
5-1. Thread는 Servlet의 service() 메서드를 호출한다.<br>
5-2. service() 메서드는 요청에 맞게 doGet() 또는 doPost() 메서드를 호출한다. <br>
> protected doGet(HttpServletRequest request, HttpServletResponse response)
6. doGet() 또는 doPost() 메서드는 인자에 맞게 생성된 적절한 동적 페이지를 Response 객체에 담아 WAS에 전달한다.
7. WAS는 Response 객체를 HttpResponse 형태로 바꾸어 Web Server에 전달한다.
8. 생성된 Thread를 종료하고, HttpServletRequest와 HttpServletResponse 객체를 제거한다.

### 참고 링크
https://gmlwjd9405.github.io/2018/10/27/webserver-vs-was.html
