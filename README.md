# java-was-2022
Java Web Application Server 2022

## 프로젝트 정보
- step3 구현완료.
- [wiki](https://github.com/jieonkim23/be-java-web-server/wiki) 에 학습내용 정리함.
### Tree
./src/main/java
```
├── Controller
│  ├── Controller.java
│  ├── FileController.java
│  ├── JoinController.java
│  └── NonController.java
├── Request
│  ├── HttpRequest.java
│  ├── HttpRequestHeaders.java
│  ├── HttpRequestParams.java
│  └── HttpRequestStartLine.java
├── Response
│  └── HttpResponse.java
├── db
│  └── Database.java
├── model
│  └── User.java
├── util
│  ├── ContentType.java
│  ├── FileIoUtil.java
│  ├── HttpRequestUtil.java
│  ├── HttpResponseUtil.java
│  ├── ManageDB.java
│  └── StatusCode.java
└── webserver
├── RequestHandler.java
└── WebServer.java
```

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.
