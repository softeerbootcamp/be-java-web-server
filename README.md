# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 프로젝트 구조
├── controller  
│   ├── Controller.java  
│   ├── HtmlController.java  
│   ├── StaticFileController.java  
│   └── UserController.java  
├── db  
│   ├── Database.java  
│   └── Session.java  
├── http  
│   ├── HttpHeader.java  
│   ├── HttpStatus.java  
│   ├── HttpUri.java  
│   ├── exception  
│   │   └── NullHttpRequestException.java  
│   ├── request  
│   │   ├── HttpRequest.java  
│   │   └── HttpRequestLine.java  
│   └── response  
│       ├── HttpResponse.java  
│       └── HttpStatusLine.java  
├── model  
│   └── User.java  
├── service  
│   ├── HtmlService.java  
│   ├── ListService.java  
│   ├── LogInService.java  
│   ├── SignUpService.java  
│   └── StaticFileService.java  
├── util  
│   ├── HttpRequestUtils.java  
│   └── HttpResponseUtils.java  
└── webserver  
├── ControllerHandler.java  
├── RequestHandler.java  
└── WebServer.java  

+ RequestHandler
  + HttpRequest 클래스에 입력을 받고 받은 URI로 알맞는 컨트롤러를 골라준다. 고른 컨트롤러로 응답을 만들고 응답을 출력해준다.
+ ControllerHandler
  + Post 요청인 경우를 먼저 확인 - 회원 가입과 로그인을 처리하는 UserController를 리턴  
  + Html 파일 요청인 경우인지 확인 - Html파일을 처리하는 HtmlController를 리턴
  + 나머지인 경우 (정적 파일) - 정적 파일을 처리하는 StaticFileController를 리턴
+ UserController
  + HttpRequest 객체로부터 uri와 httpVersion을 받아온다.
  + HttpRequest body의 query string을 파싱해서 Map 객체인 params에 넣어준다.
  + 회원 가입 요청인 경우와 로그인 요청인 경우를 나누어 준다.
  + 각각 params와 httpVersion을 나누어 주며 회원가입일 경우 SignUpService, 로그인일 경우 LogInService를 통해 응답을 생성해 준다.
  + 생성된 응답 HttpResponse 객체를 리턴한다.
+ HtmlController
  + HttpRequest 객체로부터 로그인 유저 정보, ContentType, 파일 경로, uri를 받는다.
  + 유저 리스트 출력 html일 경우 ListService를 통해 응답 생성
  + index.html 로그인 상태일 경우 HtmlService를 통해 응답 생성
  + index.html 로그인 상태가 아닐 경우 정적 파일 요청이므로 StaticFileService를 통해 응답을 생성한다.
  + 생성된 응답 HttpResponse 객체를 리턴한다.
+ StaticFileController
  + 정적 파일들은 모두 /static 폴더 내에 있으므로 기본 파일 경로를 설정해준다.
  + 설정해준 파일 경로에 HttpResquest객체로부터 받은 uri를 더해서 정적 파일 경로를 만들어준다.
  + 만들어진 파일 경로로 StaticFileService를 통해 응답을 생성한다.
  + 생성된 응답 HttpResponse 객체를 리턴한다.


## 프로젝트 학습 내용

### 2주차

---
 + Day 6
    + InputStream > InputStreamReader > BufferedReader로 Http Request Header 가져오기
    ``` java
      try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
    ```
    + BufferedReader.readLine() 메소드를 활용해 모든 Http header 출력
    + Header의 첫 번째 라인에서 요청 URL을 추출
    + Path에 해당하는 파일을 응답하기
    ``` java
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = Files.readAllBytes(new File("./src/main/resources/templates/" + url).toPath());
    ```
    + 유틸 클래스 (HttpRequestUtils) 생성 및 리팩토링
      + 테스트 코드 추가
    ``` java
        @Test
        public void getUrl(){
        String url = HttpRequestUtils.getUrl("GET /index.html HTTP/1.1");
        assertThat(url).isEqualTo("/index.html");
        }
    ```
    + 회원가입 요청 url 처리 - split 함수 활용(? split > & split > = split 처리)
      + 테스트 코드 추가
    
    + http request stream에 한글이 있을 경우가 있으므로 parseQueryString 메소드에서 UTF_8 형식으로 변경
    ``` java
        requestParamsMap.put(requestParam[0], URLDecoder.decode(requestParam[1], StandardCharsets.UTF_8));
    ```
  ---
  + Day 7
    + Refactoring : http 폴더 생성 후 필요한 클래스 생성
    + Http Request Header의 구조
      + GET /index.html HTTP/1.1  -  시작줄(method, uri, version)
      + Host: localhost:8080        -  헤더
      + Connection: keep-alive  -  헤더
      + ....
    + .trim() 함수: 문자열의 시작과 끝 공백 제거
  ---
  + Day 8
    + Entry를 활용한 May 순회 방법
    ```java
       Map<String, String> map = new HashMap<>();

       for (Map.Entry<String, String> entry : map.entrySet()) {
           String key = entry.getKey();
           String value = entry.getValue();
       }
    ```
    + split() 메소드에 정규 표현식의 예약어 사용
      + 이스케이프 문자 사용
      ```java
         split("\\?")
      ```
      + Pattern.quote() 메소드 사용
      ```java
         split(Pattern.quote("?"));
      ```
      
--------
  + Day 9
    + MIME Type
      + 인코딩(Encoding): text 파일로 변환
      + 디코딩(Decoding): text 파일을 바이너리 파일로 변환
      + Type : text, image, audio, video, application
      + subType : .avi, .bin, .css, .csv, .ico 등등..
      + Type/subType -> text/html, text/css ..
      
    + HTTP 302 FOUND response
      + 회원 가입 성공 시 
      기존의 성공 메세지와 회원 가입 정보를 byte화해서 body로 넘겨주던 로직을
      302 FOUND로 넘겨주었다
      + 302 Header
        + HTTP/1.1 302 FOUND
        + location: /index.html
        + location은 응답코드 301, 302 리다이렉션 상태에서 위치를 지정해준다
    
    + Java 객체 비교
      + ==, != 연산자
        + Primitive type를 비교할 때는 value값을 비교
        + Object를 비교할 때는 메모리 주소 비교 
      + equals() 사용
      + Objects.equals(a, b) 도 가능 (null check가 되어 있음)

    + Http Response 의 Status Line
      + HTTP version + Status code + Status text 로 구성
      ```
      HTTP/1.1 404 NOT FOUND
      ```
      
      + Java Unit Test 작성
        + given/when/then 패턴으로 깔끔하게
          + 어떤 데이터가 준비되었을 때 어떤 함수를 실행하면 어떤 결과가 나와야 한다.
        ```java
        @Test
        @DisplayName("테스트 이름")
        void test() {
            // given
            final String str = "test string"

            // when
            final String result = 테스트할클래스.method(str);

            // then
            assertThat(result).isEqualTo("예상되는 결과");
        }  
        ```
-------------
  + Day 11
    + HTTP POST 방식
      + POST 방식은 데이터 전송을 기반으로 한 요청 메서드
      + GET 방식은 URL에 데이터를 붙여서 보내지만 POST 방식은 BODY에 데이터를 넣어 보냄
      + 그래서 POST 방식에는 Content-Type 헤더 필드가 있음
        + ex) application/x-www-form-urlencoded, text/plain, multipart/form-data
      + POST Request 예시)
      ```
      POST /user/create HTTP/1.1
      Host: localhost:8080
      Connection: keep-alive
      Content-Length: 59
      Content-Type: application/x-www-form-urlencoded
      Accept: */*
      
      userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
      ```
    + JAVA 생성 패턴 [Builder 패턴]
      + 많은 Optional한 멤버 변수(파라미터)나 지속성 없는 상태 값들 처리 문제 해결
      + 구현 방법
        + 빌더 클래스를 Static Nested Class로 생성 - 관례적으로 클래스 이름 + Builder로 명명
        + 생성자는 public, 파라미터는 필수 값들
        + Optional한 값들은 속성마다 메소드로 제공, 리턴 값이 빌더 객체 자신이어야 함
        + 마지막으로 빌더 클래스 내에 build()메소드를 정의하여 최종 생성된 결과물 리턴, 
        builder를 통해서만 객체 생성을 하므로 생성 대상이 되는 클래스의 생성자는 private
-----------
  + Day 12
    + 쿠키와 세션
      + 서버는 Set-Cookie header 값으로 상태를 유지하고 싶은 값을 클라이언트에 전송
      + 클라이언트는 받은 쿠키 값을 모든 요청의 쿠키 헤더로 서버에 전송
      + 서버는 쿠키 헤더에 따라 이전 접속자인지 확인!

-----------
  + Day 13
    + Servlet
      + Java 언어로 작성된 웹 애플리케이션의 컴포넌트
      + 서버 측에서 실행되며 http 요청을 처리
      + 클라이언트의 요청을 처리하고 응답을 생성

------------
  + Day 14
    + stateful과 stateless
      + stateful
        + 상태 유지 : 서버가 클라이언트의 상태를 보존한다.
        + 대표적으로 TCP의 3-way handshaking
        + 문제점 : 해당 서버를 못쓰게 되어 다른 서버를 사용해야 할 때 발생 - 이전 서버의 정보가 없음
        + 문제점 해결 : 현업에서는 캐시 서버 Redis 사용
      + stateless
        + 서버가 클라이언트의 상태를 보존하지 않음.
        + 요청이 오면 바로 응답을 보내며 상태 관리는 클라이언트에서 책임을 짐.
        + 대량의 트래픽이 발생했을 때에도 서버 확장을 통해 대처를 수월하게 할 수 있음.
        + (stateful과 다르게 서버가 바뀌어도 정확한 응답에 문제가 없기 때문)
        + 대표적으로 UDP와 HTTP
        + 문제점 : 더 많은 데이터 소모 - 매번 요청할 때마다 부가정보 필요
    + wrapper class
      + 기본 자료타입을 객체로 다루기 위해서 사용하는 클래스
    + git diff
      + add 전과 후의 파일 내용을 비교해줌