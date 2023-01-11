# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 학습내용
### 01/09 MON
- HTTP 
  - HTTP의 첫째 줄은 [GET /index.html HTTP/1.1] 과 같은 형태이며, 이를 Request line (요청라인)이라고 부른다.
  - 요청라인의 두번째 텍스트에는 요청하는 자원이 명시되어 있다.
  - HTTP Request Message에는 Header와 Body가 있으며 이는 한줄 띄어쓰기(개행)으로 구분한다.
  - 브라우저는 응답헤더의 Content-Type 에 맞게 반환받은 파일을 인식하기 때문에 이를 잘 고려해주어야 한다.
- 소켓
  - 같은 포트로 여러개의 소켓을 열 수 있다.
  - 서버 소켓에서는 랜덤한 포트 번호를 정하고, 매번 소켓을 만들 때 마다 클라이언트에게 같은 포트번호를 전달해준다.
  - 즉, 소켓 인스턴스는 다르지만 포트번호는 같다.
  #### 소켓의 오해
    - 같은 포트에 여러개의 소켓을 가질 수 있다는 것은 알고 있었지만, 서버 소켓의 포트번호와 새로 생성된 소켓의 포트번호가 동일하다고 오해하고 있어서 개념을 이해하는데 조금 혼란이 있었따.
- Thread
  - Runnable 을 구현한 스레드 클래스의 run() 메소드를 직접 실행해도 실행은 가능하다.
  - 하지만, new Thread() 로 스레드 객체를 생성하지 않으면, 새로운 실행 흐름이 생기지 않는다.
  - 즉, 싱글 스레드 프로그램이 되고 run() 메소드가 끝날때 까지 동기적으로 블락이 걸려 run() 메소드를 호출해야하는 모든 곳에서 기다리다 순차적으로 처리가 된다.
  - 본 프로젝트에서는 웹 환경에서 다양한 사용자가 이 프로그램에 접근해야되므로 멀티 스레드가 더 적합하다.
    - 스레드를 계속해서 생성해 내는 것은 썩 좋은 방식이 아니다.
      - 스레드를 생성하고 삭제하는 작업은 비용이 아주 큰 작업이다.
      - 따라서, 스레드 풀을 이용해서 미리 동시접근 수치를 어느 정도 예상해 스레드 인스턴스를 만들어 놓고 필요할 때 가져가 사용하고 다시 스레드풀에 반환하는 방식이 더 효과적이다.

### 01/10 TUE
- HTTP
  - 상태코드를 변경하면 브라우저가 처리하는 방식이 다르다.
  - 상태코드 300번대를 주면 Location: "/index.html" 명시되어 있는 위치로 리다이렉트한다.
  - 오해 : 상태코드는 단순히 프론트엔드와 협업을 위한 코드로 오해했다.
- URL, URI, URN
  - https://www.naver.com/path/index.html
  - URL : https://www.naver.com/path/index.html
  - URI : https://www.naver.com/path/index, www.naver.com, www.naver.com/path
  - URN : path/user/1, path, /user/1
  - 참고자료 : https://www.charlezz.com/?p=44767
- HTTP Request Message
  - Accept 헤더 : 브라우저에서 인식가능한 컨텐츠 타입을 명명한다. */* <- 와일드카드
  - Content-Type 헤더 : Request, Response 둘다에 존재하고 데이터를 보낼 때 데이터의 타입을 명시해준다.
    - GET방식 경우는 Query String을 사용해 key, value쌍으로 전달하므로 Content-Type 헤더가 필요없다.
    - POST, PUT은 Body에 데이터를 포함하는 경우가 있기 때문에, Content-Type을 잘 명시해주어야한다.
    - 만약, Content-Type을 빼는 경우는 데이터를 단순 텍스트로 받아들인다.
  - Content-Length : 요청과 응답 메시지의 본문 크기를 바이트로 반환, 메시지의 크기에 따라 자동으로 만들어진다고 한다.
    - > HttpResponse를 할때 body크기를 명시하지 않으면, 자동으로 만들어지나? 확인해보자.
- MIMI
  - Multipurpose Internet Mail Extensions 약자이다.
  - Content-Type의 종류이다.
  - 대분류/소분류로 나타냄 (ex. image/gif, image/png)
  - multipart/form-data : <form> 태그를 사용해 브라우저에서 서버로 데이터 전송하는 경우
  - application/json : json format으로 데이터 전송 하는 경우
