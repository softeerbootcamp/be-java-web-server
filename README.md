# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.


## Step1(기능 및 요구사항)
- http://localhost:8080/index.html 로 접속했을 때 src/main/resources/templates 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.(step1)
- 웹 리소스 파일은 제공된 파일을 수정 및 직접 작성한다.
- Request Header를 통해 client의 경로를 확인한다.
- Files.readAllBytes를 활용하여 client요청에 맞는 리소스 파일 데이터를 보낸다.

## Step1 학습내용
- 싱글스레드와 멀티스레드에 대한 차이를 알 수 있었다.
- 현재버전에서 Java의 스레드는 OS의 스레드를 사용한다.
- Socket을 이용한 통신에는 ServerSocket, ClientSocket이 있으며, client가 요청이 오면 ClientSocket이 활성화 된다.
- Thread를 효율적으로 다루기 위해서는 ThreadPool를 사용하면 오버헤드를 줄일 수 있다.
- 왜 브라우저에는 웹소켓이 여러번 연결되는걸까?(아마 브라우저에서는 stylesheet에 대한 요청을 보내기 위해 또 다른 thread생성?)

## Step2(기능 및 요구사항)
- index.html 수정
- index.html에는 회원가입 이동 링크가 필요
- /user/form.html 수정(form태그를 활용한 회원가입 페이지생성 및 가입버튼)
- 버튼을 누르면 해당 필드값을 Get요
- Junit테스트
