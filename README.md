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