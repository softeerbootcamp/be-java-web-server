# Java web server

## 요구사항:

<br/>

- HTTP POST 동작방식 이해하기
- HTTP redirection 기능을 이해하고 회원가입 후 페이지 이동에 적용한다.
- 로그인을 GET에서 POST로 수정 후 정상 동작하도록 구현한다.
- 가입을 완료하면 /index.html 페이지로 이동한다.

<br>

## 동작 과정:

# [index.html]

- http://localhost:8080/index.html 로 접속했을 때 resources 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
  - Http Request Header를 읽는다.
  - Header에서 요청 URL을 추출한다.
  - 요청 URL path에 맞는 파일로 응답한다.

# [/user/form.html]
- “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다. 회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
- HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
- “회원가입”을 완료하면 /index.html 페이지로 이동

# [HttpRequset.class]
- 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리
    - Header/Body의 처리를 담당하는 역할을 분리
    - InputStream을 생성자로 받아 HTTP 메소드, URL, 헤더, 본문을 분리하는 작업 수행

# [HttpResponse.class]
- 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리
  -  RequestHandler 클래스를 보면 응답 데이터 처리를 위한 많은 중복을 제거해준다.

# [Controller interface]
- controller 인터페이스를 구현하는 AbstractController 추상클래스를 추가해 중복을 제거
- service() 메소드에서 GET과 POST HTTP 메소드에 따라 doGet(), doPost() 메소드를 호출

# [login/session]
- user/login.html에서 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동
- 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지
  - 성공시 : header의 Cookie header 값이 logined=true 
  - 실패시 : Cookie header 값이 logined=false
  - Set-Cookie 설정시 모든 요청에 대해 Cookie 처리가 가능하도록 Path 설정 값을 (Path=/)로 설정

<br/>

 ## 학습

<br/>

- 개념 이해

<details>
1. DataOutputStream <br/>

: FileReader는 데이터를 읽어서 숫자 데이터로 변환해줘야하지만,DataOutputStream은 데이터 변환까지 해줘서 파일을 읽고 쓰는 클래스 (즉, 프리미티브 타입의 데이터를 읽을 수 잇음)
 
  > write() 
  
  : 데이터를 파일에 쓰기

  > flush()

  : 현재 버퍼에 저장되 있는 내용을 클라이언트에 전송하고 버퍼를 비운다.<br/>

  즉, 강제로 버퍼의 내용을 전송함으로써 데드락 상태를 해제한다.<br/>

  <br/>


2. 프록시<br/>

: 클라이언트와 서버 사이에 존재하며 캐싱, 필터링, 로드 밸런싱, 인증, 로깅 등의 다양한 기능을 수행한다.<br/>

<br/>


3. 브라우저 동작 방식<br/>

> 최초에는 HTML 을 가져온다.<br/>

> HTML에서 CSS, js, 이미지에 대한 링크 정보를 추출한다.<br/>

> 추출한 정보의 URL을 이용 새로운 요청을 보낸다.<br/>

> 모든 웹 자원을 받아와서 렌더링을 시작한다.<br/>

> 1.1 은 파이프라인, 2.0은 병렬처리로 성능을 개선하였다.<br/>

<br/>




4. MIME 타입이란? <br/>

> 클라이언트에게 전송된 문서의 다양성을 알려주기 위한 메커니즘 <br/>

> 브라우저들은 리소스를 내려받았을 때 해야 할 기본 동작이 무엇인지를 결정하기 위해 MIME 타입 사용 <br/>

<br/>


5. of 패턴
- of 에 데이터를 읽은 후 생성자를 리턴해서 멤버에 값을 할당한다.

<br/>


6. InputStreamReader

- InputStream 은 1바이트만 인식해 한글을 읽지 못한다.
- 그래서 InputStreamReader는 바이트 단위로 읽어 들이는 형식을
- 문자 단위(char)로 데이터 변환시키는 중개자 역할을 한다.

<br/>


7. BufferedReader

- BufferReader 를 통해 입력 데이터가 바로 출력되기 보다는 버퍼를 통해 데이터를 묶어서 한 문장씩 읽어들일 수 있게 한다.

<br/>


8. StringBuilder

- String은 변경 불가능한 문자열이다. 그래서 문자열을 더할때마다 문자열을 연결해 새로운 문자열을 만들어내는 많은 비용이 발생한다. 이러한 문제를 스트링 빌더의 append 를 통해 해결 가능하다.

<br/>

9. reflection

- Reflection이란 클래스의 구조를 분석하여 동적 로딩을 가능하게 하는 기능
  - new Instance () : 동적 객체 생성시 사용됨
    - 기본 생성자를 호출해 객체를 생성하기 때문에 반드시 클래스에 기본 생성자가 존재해야함
    - 만약 매개변수가 있는 생성자를 호출하고 싶다면 리플랙션으로 Constructor 객체를 얻어 new Instance 진행
    
  > new Instance() = Deprecated <br>
  > -> getDeclaredConstructor().newInstance() 로 수정

  - getDeclaredConstructors(), getDeclaredFields(), getDeclaredMethods() : 클래스 생성자, 필드정보, 메소드 정보를 알고 싶을 때 사용한다

  - invoke : Method를 동적으로 실행시켜 주는 메소드

10. HTTP 메시지 구조
  - Header + Body 로 나뉨
    - Header (주소정보, 메서드 방식, 클라이언트 정보, 브라우저 정보, 접속 URL)
      - start line에는 요청이나 응답의 상태를 나타낸다.
      - 요청을 지정하거나, 메시지에 포함된 본문을 설명하는 헤더의 집합이다.
      - 헤더와 본문을 구분하는 빈 줄이 있다. 그 줄을 말한다.
    - Body (비어 있다가 필요시 데이터 정보 담김)
      - 요청과 관련된 데이터나 응답과 관련된 데이터 또는 문서를 포함 (선택적 사용됨)

11. Request Body 역할

 - Request Body 가 책임져야 하는 것 : 
   - code : API 사용 프로그램 처리 결과를 간략히 전달하는 것
     - 첫 세자리 (프로토콜 상태 코드), 넷째 자리 (타입 코드), 마지막 두 자리 (구분 코드)
   - message : 코더,개발자를 위한 메시지를 주는 것
   - result : 처리 결과 값을 넘겨주는 것
   - 세가지 요소
     - HTTP method
     - URL
     - HTTP version

 ~~~
 {
	"code": 200000,
	"message": "ok",
	"result": {
		"foo":{}
		"somethings":[{},{}]
	}
}
 ~~~

ex. 사용자 등록

~~~
# POST ~/register

{
  "email": "foo@bar.baz",
  "password": "hellowrold!@"
}

# Response Code

// 정상동작 코드
{
  "code": 201000,
  // 201 는 HTTP Status code 에서 말하는 생성을 뜻
  // 000 은 아무의미 없이 자리를 맞춰주는 코드
  "message": "ok",
  "result": {
    "user": {
      // 반드시 resource 표현
      "id": 1,
      "email": "foo@bar.baz",
      "password": "hellowrold!@"
    }
  }
}

// email 이 없을 경우
{
  "code": 412000,
  // 412 : 사전조건 실패
  // 0 : required param error
  // 00 : 이메일 - 임의로 00 은 이메일로 정한 것
  "message": "email Required in request body"
}

~~~

<br/>

12. stream

-  stream
    - Arrays.stream : 배열을 스트림으로 변환
    - map : 스트림 내 요소를 특정 값으로 변환
    - collect : list or set 으로 원하는 자료형으로 변환 ex. Collect(Collectors.toSet/toList)
    - filter : 조건에 맞는 모든 요소를 가져옴
    - flterFirst : 조건에 맞는 첫번째 요소 가져옴
    - Collectors.toMap

13. - 쿠키
    - 개념
        - 방문자 식별을 위해 사이트가 사용하고 있는 서버를 이용해 사용자 컴퓨터에 설치되는 정보 파일
        - 브라우저에 의해 저장됨
        - 같은 서버에 의해 만들어진 요청들이 Cookie HTTP 헤더 안에 포함되어 전송됨
        - 그 후 쿠키는 같은 서버에 의해 만들어진 Request들의 쿠키 헤더 안에 포함되어 전송됨
        - 만료일, 지속시간이 명시될 수 있고, 만료된 쿠키는 보내지지 않음
        - 특정 도메인이나 경로 제한을 설정할 수 있음
        - Key-Value 쌍으로 클라이언트에 저장됨
        - 서버에서 생성하며 Response Header에 Set-Cookie를 넣어 클라이언트에 전송

    - Set-Cookie: HTTP Response Header는 서버로부터 사용자 에이전트로 전송됨
      ex. Set-Cookie: <cookie-name>=<cookie-value>
      ex. Set-Cookie: yummy_cookie=choco

    - 전송과정 : 브라우저는 쿠키헤더를 사용해 서버로 이전에 저장했던 모든 쿠키들을 회신
      ex.
      GET /sample_page.html HTTP/1.1
      Host: www.example.org
      Cookie: yummy_cookie=choco; tasty_cookie=strawberry

    - 쿠키의 라이프타임
        - 브라우저는 현재 세션이 끝나는 시점을 정의
        - 세션 쿠키 : 세션이 끝날 때 삭제됨
        - 영속적인 쿠키 : Expired 속성에 명시된날짜에 삭제 / Max-Age속성에 명시된기간에 삭제
          ex. Set-Cookie: id=a3fWa; Expires=Wed, 21 Oct 2015 07:28:00 GMT;

    - Secure : HTTPS 프로토콜 상에서 암호화된 요청일 경우에만 전송됨
    * 단, 본질적으로는 안전하지 않으므로 민감한 정보는 쿠키에 저장하면 안됨
    - HttpOnly : 자바스크립트의 Document.cookie 에 접근할 수 없음
      - ex. Set-Cookie: id=a3fWa; Expires=Wed, 21 Oct 2015 07:28:00 GMT;           Secure; HttpOnly

    - 쿠키의 스코프
    1) Domain : 쿠키가 전송되게 될 호스트 명시 (명시되지 않으면 문서 위치 호스트 일부가 기본값)
    2) Path : Cookie 헤더를 전송하기 위해 요청되는 URL 내에 반드시 존재해야하는 URL 경로
        - ex. /docs => /docs, /docs/Web 이 모두 포함됨

    - 쿠키의 각종 옵션 정리
        - key=value : 쿠키명,쿠키값 쌍으로 이뤄진 문자열 쿠키
        - Expires : 쿠키 만료 기간
        - domains : 쿠키가 전송될 도메인 특정
        - path=URL : 쿠키가 전송될 URL 특정
        - Secure : HTTPS일 경우 쿠키가 전송됨
        - HttpOnly : JS에서 쿠키에 접근할 수 없도록 설정

    - JSESSIONID
        - 톰캣 컨테이너에서 세션을 유지하기 위해 발급하는 키
        - 상태 저장하기 위해 톰캣은 JSESSIONID 쿠키를 클라이언트에게 발급
        - 이 값을 통해 세션을 유지할 수 있음

    - JSESSIONID 동작 방식
        - 브라우저 최초 접근시Response 헤더에 JSESSIONID 값이 발급됨
        - 브라우저 재요청시 Response를 통해 받은 JSESSIONID 를
          Request 헤더 쿠키에 값을 넣어 서버에 요청됨
            * 쿠키를 통해 JSESSIONID값을 전달받게 되면 새로운 JSESSIONID 값을 Response 헤더에 발급하지 않음
        - 클라이언트로부터 전달받은 JSESSIONID값을 기준으로 서버에선 세션 메모리 영역에 상태 유지 값들을 저장함
        - 유지 범위 : 서브도메인이 다르면 쿠키가 유지되지 않으므로 동일한 도메인
        
</details>

<br/>

<br/>

- 수업 노트

<details>

- 리퀘스트 핸들러를 왜 쓰레드로 생성했는가? <br/>

    - runnable의 run : 스레드를 실행 <br/>

    - thread 의 start: 스레드를 생성 <br/>

 <br/>

<br/>

- 왜 멀티스레드로 생성하나?<br/>

    - 많은 사람들이 사용하게 하기 위함<br/>

    - thread.start 안에 runnable 하지 않는다면 싱글 스레드로 한명만 실행 가능<br/>
      <br/>
      <br/>

- 그렇다면 언제 싱글 스레드를 사용하는가?

    - 채팅 메시지와 같은 동시에 접근할 필요가 없는 p2p통신

  <br/>

- 쓰레드를 만들고 없애는 비용은 엄청 크다.
   <br/>

    - 쓰레드를 계속 생성하고 삭제하는게 아님
    - 쓰레드 풀에 미리 쓰레드를 만들어 놓고 하나씩 빼내서 쓰는게 좋다.<br/>
      
<br/>

- 풀 리퀘스트를 웬만하면 해라

    - PR 을 하면 revert가 가능
    - reset 으로 커밋을 돌리면 작업했던 내용이 다 날라감
    - PR을 하면 돌리기 전 작업 내역도 남아 있음

</details>

<br/>

<br/>

- Pull Request 피드백

<details>

- 해야 할 일은 주석 "/TODO" 라고 해서 인텔리J의 도움을 받는다. <br/>
( 주로, 메소드 몸통 안에 많이 사용한다. )

<br/>

- logger.info/debug/error 등 로거 레벨에 대해 공부한다.

  - trace
    : 프로그램 실행되면서 발생하는 모든 것을 추적함
    시스템 성능이 느려질 수 있어 trace는 잘 사용안함

  - debug
    : 프로그램이 실행되는 과정에서 값을 찍어보고 싶을 때 사용

  - info
    : 프로그램에 실행시 정보들을 출력하는 default 값이다.

  - warn
    : 경고성 콘솔들이 출력됨

  - error
    : 에러 발생 시, 콘솔에 로그를 찍는 로그 레벨
      * try catch 문에서 e.printstacktrace 는 콘솔에 로그를 찍고
        IO 발생시키기 때문에 성능에 악영향을 미치기 때문에 로거를 이용함
      * 보통 try catch 문에 넣어서 사용하므로 무조건 실행되는 SOUT 보단 좋은 성능이다.

<br/>

- 유틸리티 메서드는 static으로 만들어, static import를 사용해 좀 더 편리하게 사용한다.

<br/>

- StringParser class 로 분리

- NIO는 무엇이고, NIO에서 파일 읽을 때 Paths.get() 을 이용하는게 낫지 않은가?


- NIO 가 무엇인가
  New Input Output 약자이다.
  채널이 양방향 버퍼를 통해 외부 데이터와 통신한다.

  -IO와 차이점
    - IO와 달리 읽기/쓰기를 하나의 통로로 해결한다.
    - IO는 데이터가 흘러가는 통로가 Stream, NIO는 통로가 channel

    - stream vs channel
      - stream
        > Input/Ouput Stream 을 구분해서 사용함 ( 단방향 )
        > 데이터 단위가 바이트 단위이다.
        > 읽기 쓰기 작업시 blocking 된다.

      - channel
        > 양방향이라 소켓,파일채널 등의 객체 하나로 입/출력이 가능
        > ByteBuffer 객체를 통해 데이터를 읽고씀
        > 읽기 쓰기 작업시 non-blocking 된다.


- blocking 방식이란?
  - 블로킹 되면 쓰레드는 프로세스를 실행하고 있지 않은 상태로 유지됩니다.
  - 네트워킹처럼 오래걸리는 작업은 블로킹으로 작업 수행하면 병목의 원인이 된다.

 ```
 [ 예시 ]
 public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection));
                thread.start();
            }
        }
    }
 ```

- 정리

위의 blocking 방식의 예제를 보면, <br/>
connect socket이 accept 되기 전까지는 그 아래 기능은 동작하지 못합니다. <br/>
그래서 클라이언트는 언제 전송될지 모르는 데이터를 하염없이 기다려야 합니다. <br/>
이러한 문제를 해결하기 위해 다수의 클라이언트가 걸어오는 요청을 감당하기 위해서 ServerSocket channel, Sockey Channel 당 하나의 스레드를 할당합니다. <br/>
그러면 클라이언트 하나당 1개의 스레드를 소모하는데, 스레드 풀을 통해 다수의 클라이언트 연결 요청을 대비합니다. <br/>



<br/>

- Non-blocking 방식?
  - API호출 시 작업 완료 여부와 상관없이 즉각적으로 현재 상태에 대한 응답이 옴
  - API호출 후 쓰레드 제어권이 있기 때문에 다른 작업 진행할 수 있음
  
  - 정리
  aceept(), read() 등의 메서드를 호출해도 블락되지 않습니다. <br/>
  이벤트가 들어오면 콜백 함수를 실행시키는 방식으로 비동기 처리를 하게 됩니다.<br/>
  이러한 특징은 다수의 쓰레드로 IO를 관리하는 방식에 비해 쓰레드 스위칭 비용을 줄이기 때문에 이점을 줍니다. <br/>
  그렇다면 당장 이벤트가 발생했는지 확인하는 리스너가 필요합니다. <br/>
  이를 위해 Selector 라는 이벤트 리스너를 제공합니다. <br/>
    - Selector : 멀티 채널 작업을 싱글 스레드에서 처리할 수 있도록 멀티플렉서도 제공
  

- 파일 끝 자동 개행 기능 추가
  - https://velog.io/@d-h-k/intellij-%ED%8C%8C%EC%9D%BC%EB%81%9D%EC%97%90-%EA%B0%9C%ED%96%89%EC%9D%84-%EC%9E%90%EB%8F%99%EC%9C%BC%EB%A1%9C-%EC%B6%94%EA%B0%80%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95
  - 설정 > editor > general > Ensure every saved file ends 체크

</details>




