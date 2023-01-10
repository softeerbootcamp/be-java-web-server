# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 학습 내용
InputStream : 브라우저에서 서버쪽으로 오는 모든 요청 입력(BufferedReader로 변환)
- BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
OutputStream : 서버에서 클라이언트에게 응답을 보낼때 

1. GET /index.html HTTP/1.1
   메소드 / 주소 / HTTP 버전

2. Host: localhost:8080
   서버의 도메인 네임

3. Connection: keep-alive
   
4. Accept: */*
   클라이언트가 허용할 수 있는 파일 형식