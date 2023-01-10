# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.


## 생각하고 있는 프로젝트 구조
1. RequstHandler가 입력을 받으면, 그대로 CustomHttpRequest객체와 CustomHttpResponse객체를 만들어 RequestRouter에 넘긴다.
2. RequestRouter는 Req객체를 보고, 해당 요청을 처리할 Service와 Method를 찾는다.
3. Service는 원하는 파일, 정보를 만들거나 찾아서 리턴한다.