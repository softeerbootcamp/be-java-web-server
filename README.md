# java-was-2022
Java Web Application Server 2022

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.
----
## 프로젝트 구조
```
src/main/java
├── controller
│   ├── CommentController.java
│   ├── ControllerMapping.java
│   ├── RequestDispatcher.java
│   ├── UserController.java
│   └── ViewController.java
├── db
│   ├── CommentDatabase.java
│   ├── SessionDatabase.java
│   └── UserDatabase.java
├── dto
│   ├── Comment.java
│   ├── HttpRequest.java
│   ├── HttpResponse.java
│   ├── Session.java
│   └── SessionCookie.java
├── model
│   └── User.java
├── service
│   ├── CommentService.java
│   ├── DBConnection.java
│   ├── SessionService.java
│   ├── TemplateRenderer.java
│   └── UserService.java
├── utility
│   ├── CredentialsParser.java
│   ├── HttpMethodType.java
│   ├── HttpRequestUtils.java
│   └── HttpStatusCode.java
└── webserver
    ├── RequestHandler.java
    └── WebServer.java
```

* controller - 요청에 대한 처리를 담당하는 컨트롤러 클래스 포함
  * CommentController - 한 줄 코멘트 작성 요청 처리를 담당
  * RequestDispatcher - 각 요청을 처리할 수 있는 Controller의 메서드를 찾고 호출
  * ControllerMapping - 요청에 맞는 컨트롤러를 리플렉션을 통해 찾을 수 있도록 돕는 Annotation
  * UserController - 계정 로그인, 회원가입 등의 요청 처리를 담당
  * ViewController - 동적 페이지 생성 등의 요청 처리를 담당


* service - DB연결, 세션, 동적 페이지(템플릿), 유저 관련 서비스 레이어
* utility - Request의 파싱, xml로 저장된 계정 정보의 파싱 등을 수행
## 프로젝트 설정

----
### DB 연결

```docker run --name {mysql-container} -e MYSQL_ROOT_PASSWORD={password} -p 3306:3306 -d mysql:8.0.32```

```docker exec -it mysql-container bash```

## 프로젝트 실행
`WebServer.main()` 실행

## HTTP 프로토콜
![w301.png](ReadmeImg%2Fw301.png)
웹에서 통용되는 데이터 교환의 근간이 되는 **클라이언트** - **서버** 프로토콜이다.

![w3-1.gif](ReadmeImg%2Fw3-1.gif)

클라이언트로부터 웹 서버로 전달된 메시지는 Request, 웹 서버에서 클라이언트의 요구를 처리해 다시 클라이언트로 반환하는 메시지는 Response로 불린다.

### HTTP Request / Response
![w302.png](ReadmeImg%2Fw302.png)

HTTP 프로토콜을 이용한 원활한 데이터 교환을 위해 각 HTTP request / response 메시지를 이용해 요청 자원의 기술과 반환할 자원, 및 결과의 상태 코드를 반환한다.

### HTTP Request
![w303.png](ReadmeImg%2Fw303.png)

HTTP 요청의 첫 줄은 Request Line으로, 

Request Line : **HTTP 메서드**, **path**, **HTTP 버전**

으로 구성된다.
* **HTTP 메서드** : 클라이언트가 `원하는 동작`을 기술 (e.g., 자원의 요청, 데이터 전송 등)
* **path** : 원하는 `자원으로의 위치`를 지정
* **HTTP 버전** : 사용하는 프로토콜의 버전

### HTTP Response
![w304.png](ReadmeImg%2Fw304.png)

Content-Type 헤더: 리스폰스 body가 데이터의 타입
* Request의 Accept 헤더에 대응되며 Http Response body 내용의 타입을 설명하는 헤더
----

## Java Thread
### Java에서 Thread 생성
* `Runnable` 인터페이스를 구현, `run()` 메서드 오버라이딩
* `Thread` 클래스 상속

Java Thread 객체는 시작 혹은 정지만 가능하고, 재시작과 같은 동작은 허용되지 않는다.

`thread.start()` 호출시 새로운 쓰레드가 생성되며, 전달된 Runnable 타입 객체의 run() 메서드가 호출된다.

> **_note:_** 직접 Runnable의 run() 메서드를 호출시, 작업이 실행은 되지만, 새로운 쓰레드가 생성 후 실행되지 않고 현재 실행 흐름(main 쓰레드에서) 작업이 실행된다.


### 프로젝트 적용
이 애플리케이션에서는 여러 자원에 대한 요청을 동시에 처리해 처리율을 높일 수 있다. 따라서 멀티 스레딩을 이용한 요청 처리가 효과적이다.

Thread를 통해 처리율은 높일 수 있지만, 여러 Thread가 부분별하게 생성 가능하기 때문에 Thread 생성에서 오는 오버헤드가 존재한다. 
이는 일정 수의 Thread를 미리 생성, 필요할 때 빌려 사용하고 사용이 끝나면 다시 Thread를 반납하는 ThreadPool을 통해 경감시킬 수 있다. 



### 
![img1.daumcdn.png](ReadmeImg%2Fimg1.daumcdn.png)