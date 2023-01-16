# java-was-2022
Java Web Application Server 2022

## 프로젝트 구조
    ├── java
    │   ├── controller
    │   │   ├── Controller.java
    │   │   ├── ControllerSelector.java
    │   │   ├── StaticController.java
    │   │   ├── TemplateController.java
    │   │   └── UserController.java
    │   ├── db
    │   │   └── Database.java
    │   ├── enums
    │   │   ├── ContentTypeEnum.java
    │   │   ├── ControllerTypeEnum.java
    │   │   ├── HeaderReferenceEnum.java
    │   │   ├── HttpVersionTypeEnum.java
    │   │   └── StatusCodeWithMessageEnum.java
    │   ├── model
    │   │   └── User.java
    │   ├── request
    │   │   ├── Request.java
    │   │   ├── RequestHeader.java
    │   │   ├── RequestLine.java
    │   │   └── RequestURL.java
    │   ├── response
    │   │   ├── Response.java
    │   │   ├── ResponseHeader.java
    │   │   └── ResponseStatusLine.java
    │   └── webserver
    │       ├── RequestHandler.java
    │       └── WebServer.java


## 기능 설명
### webserver
    WebServer.java
socket을 형성하고, 스레드를 형성하는, 서버 시작점.

    RequestHandler.java


### controller
    controller.java
각각의 controller 들의 주요 기능인 ser