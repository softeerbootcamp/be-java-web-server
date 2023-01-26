# java-was-2022
Java Web Application Server 2022

## 프로젝트 정보
- step7 구현완료.
- [wiki](https://github.com/jieonkim23/be-java-web-server/wiki) 에 학습내용 정리함.
### Tree
```
../src/main/java
├── Controller
│ ├── AuthController.java
│ ├── Controller.java
│ ├── FileController.java
│ ├── IndexController.java
│ ├── JoinController.java
│ ├── LoginController.java
│ ├── MatchController.java
│ ├── NonController.java
│ ├── QnaCreateController.java
│ ├── QnaShowController.java
│ └── UserListController.java
├── Exception
│ └── NotExistFileException.java
├── Request
│ ├── HttpMethod.java
│ ├── HttpRequest.java
│ ├── HttpRequestHeaders.java
│ ├── HttpRequestParams.java
│ ├── HttpRequestStartLine.java
│ └── StatusCode.java
├── db
│ ├── DBConnector.java
│ ├── Database.java
│ └── SessionDb.java
├── model
│ ├── Qna.java
│ ├── Session.java
│ └── User.java
├── repository
│ └── QnaRepository.java
├── response
│ ├── ContentType.java
│ ├── HttpResponse.java
│ ├── HttpResponseBody.java
│ ├── HttpResponseHeaders.java
│ └── HttpResponseStartLine.java
├── util
│ ├── FileIoUtil.java
│ ├── HtmlBuildUtil.java
│ ├── HttpRequestUtil.java
│ ├── HttpResponseUtil.java
│ ├── LoginUtil.java
│ └── UserDbUtil.java
├── view
│ ├── FileView.java
│ ├── IndexView.java
│ ├── QnaShowView.java
│ ├── UserListView.java
│ └── View.java
└── webserver
    ├── RequestHandler.java
    └── WebServer.java
./src/test
├── http
│ └── test.http
└── java
    ├── controller
    │ ├── FileControllerTest.java
    │ └── JoinContorllerTest.java
    ├── repo
    │ └── QnaRepositoryTest.java
    └── util
        ├── FileIoUtilTest.java
        ├── HtmlBuildUtilTest.java
        ├── HttpRequestUtilTest.java
        ├── HttpResponseUtilTest.java
        ├── LoginUtilTest.java
        └── UserDbUtilTest.java
```

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.
