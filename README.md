# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## Step 1 - index.html 응답
최초의 상태는 localhost:8080으로 접속 시에 "Hello World"를 출력해준다. 이는 구현되어 있는 responseBody 메서드에 해당 문구를 byte[]로 변환하여 응답해주기 때문이다.

응답으로 index.html을 넘겨주는 방식으로 개선하기 위해서는 
1. 서버로 들어오는 inputLine에서 해당 url을 parsing
2. parsing한 파일을 resources에서 찾아 byte[]로 변환
3. 이를 response body에 write

의 과정을 거쳐야 한다.
index.html에 대한 HTTP Request 메시지의 첫 라인은 **GET /index.html HTTP/1.1**이다. 이것을
1. split메서드를 통해 "/index.html"만을 parsing
2. "index.html"이 있는 resource 디렉토리를 붙인 절대 경로로 Files.readAllBytes() 메서드의 인자로 넘겨 불러와 byte[]로 변환
3. 이를 response body에 write

하여 해결 가능하다.
