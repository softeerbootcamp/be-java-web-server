# java-was-2023
Java Web Application Server 2023

## 현재 작업중인 것 (week4 branch)
### 기능구현
1. index.html에 로그인한 사용자가 글을 쓸 수 있는 한 줄 메모장을 구현한다.
2. 로그인하지 않은 사용자도 게시글을 볼 수 있다.
3. 로그인한 사용자만 글을 쓸 수 있다.



## 프로젝트 구조
    ├── controller
    │   ├── Controller.java
    │   ├── ControllerSelector.java
    │   ├── DynamicController.java
    │   ├── LoginController.java
    │   ├── StaticController.java
    │   ├── TemplateController.java
    │   └── UserController.java
    ├── db
    │   └── Database.java
    ├── enums
    │   ├── ContentTypeEnum.java
    │   ├── ControllerTypeEnum.java
    │   ├── HeaderReferenceEnum.java
    │   ├── HttpVersionTypeEnum.java
    │   ├── MethodEnums.java
    │   └── StatusCodeWithMessageEnum.java
    ├── model
    │   └── User.java
    ├── request
    │   ├── Request.java
    │   ├── RequestBody.java
    │   ├── RequestHeader.java
    │   └── RequestLine.java
    ├── response
    │   ├── ResponseAssembler.java
    │   ├── ResponseBody.java
    │   ├── ResponseFactory.java
    │   ├── ResponseHeader.java
    │   ├── ResponseSender.java
    │   └── ResponseStatusLine.java
    ├── session
    │   ├── HttpSession.java
    │   └── HttpSessions.java
    ├── utils
    │   ├── StringBuilderUtils.java
    │   └── UserInfoParseUtils.java
    └── webserver
    ├── RequestResponseHandler.java
    └── WebServer.java



# 기능 설명
## update 필요.
## webserver
    WebServer.java
socket을 형성하고, 스레드를 형성하는, 서버 시작점.

    RequestResponseHandler.java
들어온 요청을 분석하여, response 를 해주는 handler. <br>
기능 분리나 명칭이 바뀔 필요가 있다고 생각한다.

## request
    Request
request 객체

    RequestHeader
header 부분을 파싱하는 부분. 현재는 불필요하므로 미구현

    RequestLine
line 부분을 파싱하는 부분.

    RequestUrl
url 부분을 처리하는 클래스. 현재는 불필요하므로 미구현




## controller
    Controller.java
각각의 controller 들의 주요 기능인 controllerService 의 부모객체 <br>
ControllerSelector 를 통해 각각의 service들을 호출할 수 있게 한다.<br>
각각의 service들은 response를 진행한다.

    ControllerSelector.java
파리미터로 들어온 request 의 정보에 따라 어떤 Controller를 사용할지 결정해주어 그에 합당한 Controller를 반환해주는 클래스

    StaticController.java
정적인 파일을 요청받는 경우 사용되는 Service. css, js 와 같은 파일들을 처리한다.<br>

    TemplateController.java
template 파일을 요청 받는 경우 사용되는 클래스. 

    UserController.java
User 요청을 처리하는 클래스. <br>
현재 user 정보를 String 형태로 각각 parse 하여 진행해 두었지만, 추후 Validation Check 등에서 불편할 수 있으므로, Map 형태로 바꾸어 보자.

## resoponse
    Response 
header, statusLine 의 정보가 담겨있는 response 객체 클래스.

    ResponseHeader
Content Type 을 파라미터로 받아와 Header를 작성하는 클래스.

    ResponseStatusLine
Controller Type 을 파라미터로 받아와 상태 코드와 사유를 작성하는 클래스.

## db
    Database
현재는 참조정도로만 사용되는 데이터베이스 부분.

## enums
    ContentTypeEnum
    ControllerTypeEnum
    HeaderReferenceEnum
    HttpVersionEnum
    StatusCodeWithMessageEnum
명칭에 맞는 열거형 정보들이 존재함.


