# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.


## 프로젝트 구조
```
├── main
│   ├── java
│   │   ├── Utility
│   │   │   ├── HtmlMakerUtility.java
│   │   │   ├── PostValidation.java
│   │   │   └── UserValidation.java
│   │   ├── controller
│   │   │   ├── PostController.java
│   │   │   ├── RequestController.java
│   │   │   ├── StaticFileController.java
│   │   │   └── UserAccountController.java
│   │   ├── exceptions
│   │   │   └── CustomException.java
│   │   ├── httpMock
│   │   │   ├── CustomHttpFactory.java
│   │   │   ├── CustomHttpRequest.java
│   │   │   ├── CustomHttpResponse.java
│   │   │   └── constants
│   │   │       ├── ContentType.java
│   │   │       ├── HttpMethod.java
│   │   │       └── StatusCode.java
│   │   ├── model
│   │   │   ├── Post.java
│   │   │   ├── Session.java
│   │   │   └── User.java
│   │   ├── repository
│   │   │   ├── DBConnectionManager.java
│   │   │   ├── DBPostRepo.java
│   │   │   ├── DBUserRepo.java
│   │   │   ├── MemorySessionRepo.java
│   │   │   ├── MemoryUserRepo.java
│   │   │   ├── PostRepo.java
│   │   │   ├── SessionRepo.java
│   │   │   └── UserRepo.java
│   │   ├── service
│   │   │   ├── PostService.java
│   │   │   ├── SessionService.java
│   │   │   ├── StaticFileService.java
│   │   │   └── UserService.java
│   │   └── webserver
│   │       ├── RequestHandler.java
│   │       ├── RequestRouter.java
│   │       └── WebServer.java
│   └── resources
└── test
    └── java
        ├── Repository
        │   └── DBConnectionTest.java
        ├── controller
        │   ├── StaticFileControllerTest.java
        │   └── UserAccountControllerTest.java
        ├── model
        │   └── SessionTest.java
        ├── service
        └── webserver
            └── RequestRouterTest.java

```

기본적인 동작 순서는 다음과 같습니다.

WebServer -> RequestHandler -> RequestRouter -> someController -> someService -> someRepository 

1. Server  : 요청을 받으면 새로운 Thread로 RequestHandler를 만들고 input, output stream을 넘긴다.
2. Handler : 받은 stream을 통해서 req 객체를 만들고 Router에게 보낸다.
3. Router  : 핸들러에게 받은 req를 가지고 올바른 controller에게 그 요청을 보낸다.
4. Controller : 라우터에게 받은 req를 분해하고 service들에게 명령을 내린다. 이후 결과를 조직해 Res객체를 생성
5. Service : 컨트롤러로 부터 정보를 받아 작업을 하고, 결과를 돌려준다.
6. Repository : 직접 DB로 부터 객체를 꺼내고 넣는 역할을 진행한다.
   1. 레포지 토리중 DBConnectionManager는 SQL을 쏘고, 그 결과를 2차원 string 형태로 받는일만 하는 객체입니다.







- utiltiy: 간단한 validation 체크 코드들이 들어가 있는 곳입니다.
- controller: RequestController를 implement하고 있다.
  - StaticFileController: StaticFileService를 이용해 파일을 받아서 response를 만든다.
  - UserAccountController: DB로 부터 데이터를 받아오거나 DB에 정보해 response를 만든다.
- service: 직접적으로 데이터를 받아서 동작을 수행하고 결과를 리턴하는 Service 클래스들
  - UserService: 유저 데이터를 다루는 유저 서비스 객체
  - StaticFileService: 정적파일들을 다루는 서비스객체
- repository : 직접적으로 DB를 가지고 object로 만들거나, object를 DB에 저장하는 일을 함.

- httpMock: http req, res를 나타내는 개체들이 있는 패키지
  - CustomHttpRequest: http 요청을 객체화 한 것
  - CustomHttpResponse: http 응답을 객체화 한 것
  - constants: httpstatuscode, contentType등 정해진 상수를 부를때 사용함.
- model: 직접적인 엔티티 객체들이 있는 곳.
- webserver:
  - RequestHandler: 소켓통신으로 받은 stream을 이용해 req, res만들어 router로 전송함.
  - RequestRouter: 받은 req, res로 적절한 컨트롤러를 찾아감.
  - WebServer: 연결 요청을 받을때마다 handler를 생성하는 메인 클래스

