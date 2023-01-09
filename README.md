# java-was-2022
Java Web Application Server 2022

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