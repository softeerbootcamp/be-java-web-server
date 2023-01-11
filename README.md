# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.


## 프로젝트 구조
```
.
├── main
│   ├── java
│   │   ├── controller
│   │   │   ├── RequestController.java
│   │   │   ├── StaticFileController.java
│   │   │   └── UserAccountController.java
│   │   ├── db
│   │   │   ├── Database.java
│   │   │   └── StaticFileService.java
│   │   ├── httpMock
│   │   │   ├── CustomHttpRequest.java
│   │   │   ├── CustomHttpResponse.java
│   │   │   └── constants
│   │   │       ├── ContentType.java
│   │   │       └── StatusCode.java
│   │   ├── model
│   │   │   └── User.java
│   │   └── webserver
│   │       ├── RequestHandler.java
│   │       ├── RequestRouter.java
│   │       └── WebServer.java
│   └── resources - 생략
└── test
    └── java
        └── service
            └── RegexTester.java
```

- controller: RequestController를 implement하고 있다.
  - StaticFileController: StaticFileService를 이용해 파일을 받아서 response를 만든다.
  - UserAccountController: DB로 부터 데이터를 받아오거나 DB에 정보해 response를 만든다.
- db: 직접적으로 데이터를 받아서 동작을 수행하고 결과를 리턴하는 Service 클래스들
  - Database: 나중에 이름 수정해서 userService가 될 예정
  - StaticFileService: 정적파일들을 다루는 서비스객체
- httpMock: http req, res를 나타내는 개체들이 있는 패키지
  - CustomHttpRequest: http 요청을 객체화 한 것
  - CustomHttpResponse: http 응답을 객체화 한 것
  - constants : httpstatuscode, contentType등 정해진 상수를 부를때 사용함.
- model: 직접적인 엔티티 객체들이 있는 곳.
- webserver:
  - RequestHandler: 소켓통신으로 받은 stream을 이용해 req, res만들어 router로 전송함.
  - RequestRouter: 받은 req, res로 적절한 컨트롤러를 찾아감.
  - WebServer: 연결 요청을 받을때마다 handler를 생성하는 메인 ㅋ르래스

