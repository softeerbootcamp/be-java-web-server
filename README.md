# Java web server

 ## 집중했던 부분: 

<br/>

 1) socket 통신의 개념적인 이해
 2) 쓰레드 사용
 3) 유지보수 좋은 코드 작성하기


<br/>

 ## 1일차

<br/>

 ### 클래스 역할

<br/>

 - RequestHandler : 클라이언트에서 요청이 들어오면 요청한 파일을 다시 클라이언트로 전달해준다.

<br/>

 ### 학습

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

<br/>

3. 브라우저 동작 방식<br/>

> 최초에는 HTML 을 가져온다.<br/>

> HTML에서 CSS, js, 이미지에 대한 링크 정보를 추출한다.<br/>

> 추출한 정보의 URL을 이용 새로운 요청을 보낸다.<br/>

> 모든 웹 자원을 받아와서 렌더링을 시작한다.<br/>

> 1.1 은 파이프라인, 2.0은 병렬처리로 성능을 개선하였다.<br/>

<br/>

<br/>


4. MIME 타입이란? <br/>

> 클라이언트에게 전송된 문서의 다양성을 알려주기 위한 메커니즘 <br/>

> 브라우저들은 리소스를 내려받았을 때 해야 할 기본 동작이 무엇인지를 결정하기 위해 MIME 타입 사용 <br/>

<br/>

<br/>

</details>

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

  ~~~
  [ 구현해야 함 ]
  
  ~~~
  
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




