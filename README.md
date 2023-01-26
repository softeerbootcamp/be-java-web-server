# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 학습 내용
InputStream : 브라우저에서 서버쪽으로 오는 모든 요청 입력(BufferedReader로 변환)
- BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
OutputStream : 서버에서 클라이언트에게 응답을 보낼때 

1. GET /index.html HTTP/1.1
   메소드 / 주소 / HTTP 버전

2. Host: localhost:8080
   서버의 도메인 네임

3. Connection: keep-alive
   
4. Accept: */*
   클라이언트가 허용할 수 있는 파일 형식

## 프로젝트 구조
```
── src
    ├── main
    ├── java
    ├── controller
    │      ├── Controller.java
    │      ├── ControllerHandler.java
    │      ├── DynamicController.java
    │      ├── StaticController.java
    │      └── UserController.java
    ├── db
    │    └── Database.java
    ├── exception
    │      ├── ControllerNotFoundException.java
    │      └── ResourceTypeNotFoundException.java
    ├── http
    │     ├── request
    │     │      ├── HttpMethod.java
    │     │      ├── HttpRequest.java
    │     │      ├── HttpUri.java
    │     │      ├── QueryParameters.java
    │     │      ├── RequestHeader.java
    │     │      ├── RequestLine.java
    │     │      └── ResourceType.java
    │     ├── response
    │     │      ├── DynamicResolver.java
    │     │      ├── HttpResponse.java
    │     │      ├── HttpResponseFactory.java
    │     │      └── HttpStatus.java
    │     └── session  
    │            ├── HttpResponseFactory.java
    │            └── HttpStatus.java
    ├── model
    │      └── User.java
    ├── service
    │      └── UserService.java
    ├── util
    │      └── FileIoUtil.java
└── webserver
├── RequestHandler.java
└── WebServer.java
```

동작 과정
WebServer -> RequestHandler -> ControllerHandler -> Controller -> Service

- webserver
   - WebServer : 클라이언트가 접속시 새로운 Thread로 RequestHanlder를 생성
   - RequestHandler : 전달 받은 socket을 이용해서 HttpRequest를 만들고 ControllerHandler에게 전달
- controller
   - ControllerHandler : HttpRequest를 기준으로 해당 Controller를 찾은 뒤 doService 메소드 호 후 응답 생성
   - Controller : 컨트롤러 인터페이스
   - StaticController : 정적 타입 파일을 처리하는 컨트롤러
   - DynamicController : 동적 타입 파일을 처리하는 컨트롤러
   - UserController : 회원 관련 데이터를 처리하는 컨트롤러, 요청에 해당하는 UserService의 메소드를 찾아 실행
- service
   - UserService : 로그인, 회원가입, 로그아웃, 회원 목록 출력 기능 수행
- http
   - request
      - HttpRequest : Http 요청 클래스(RequestLine, RequestHeader, RequestBody로 구성)
      - RequestLine : RequestLine 클래스(HttpMethod, HttpUri, HttpVersion로 구성)
      - HttpMethod : HttpMethod를 ENUM으로 선언
      - HttpUri : HttpUri 클래스(url과 QueryParameters로 구성)
      - QueryParameters : 쿼리 스트링의 파라미터 클래스
      - RequestHeader : RequestHeader 클래스
      - ResourceType : 정적 리소스 타입 ENUM으로 선언
   - response
      - HttpResponse : Http 응답 클래스(HttpStatus, contentType, header로 구성)
      - HttpResponseFactory : HttpResponse를 생성해주는 클래스
      - DynamicResolver : 동적 html을 렌더링 해주는 클래스
      - HttpStatus : HttpStatus를 Enum으로 선언
   - session
      - HttpSession : 세션 정보를 저장
      - SessionHandler : 전체 세션을 저장하고, 세션 정보를 관리함
- db
   - Database : User 정보에 접근하는 DB 역할을 수행
- model
   - UserService : 회원가입, 로그인, 리스트 출력, 로그아웃 등의 역할을 수행하는 클래스
- util
   - FileIoUtil : 파일을 불러오는 역할을 수행.