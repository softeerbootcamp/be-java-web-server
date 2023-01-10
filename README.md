# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 학습한 내용
<details>
<summary>DNS의 간략한 동작 방법</summary>
<div markdown="1">

1. 검색 창에 www.naver.com 입력 후 엔터 탁
2. 브라우저와 운영체제는 먼저 www.naver.com 의 IP 주소가 캐싱되어 있는지 체크
3. 캐싱되어 있지 않다면 Resolver를 통해 Root DNS 서버의 IP 주소를 획득
    1. Resolver는 주로 ISP(Internet Service Provider, ex. KT, SKT, U+..)로 Root DNS 서버의 주소를 알고 있음
4.  Root를 통해  Resolver로 Top-level DNS 서버의 주소를 획득
    1. 2가지 방식 존재
        1. Root가 직접 하위 서버에 쿼리를 날려 최종 목표 IP 주소를 Resolver에게 전달하는 방식
            1. Root 서버는 전 세계 13개 뿐이므로 많은 부하가 발생 - 이 방법은 사용 되지 않음
        2. Resolver가 각 서버에서 반환하는 주소로 직접 쿼리를 날려 최종 IP 주소 획득 - 현재 사용하는 방법, 즉 아래 설명된 방법
5. Top-level DNS 서버를 통해 다시 Resolver로 authoritative name server 주소 획득
6. Authoritative name server를 통해 www.naver.com의 IP 주소 획득 가능
    1. 일반적으로 한 Domain Name의 IP를 여러 authoritative name server가 가지고 있는 형태 (in case of failure)
7. 획득한 IP 주소를 이용해 사용자 request 전송 및 IP 주소 캐싱

</div>
</details>

<details>
<summary>클라이언트의 요청에 따른 웹 서버의 동작과 웹 서버의 응답에 따른 브라우저의 동작</summary>
<div markdown="1">
 
* 클라이언트가 주소 창에 localhost:8080/index.html이라는 URI를 입력
  * URI = URL + URN, URL은 localhost:8080 - resource의 위치, URN은 index.html - resource의 이름을 의미
  * 즉 URI는 URL과 URN을 통합한 것으로 특정 위치의 특정 파일을 의미, ex. localhost:8080의 index.html
* 웹 서버는 요청을 받고, 해당 위치에 파일이 존재하면 응답의 body에 해당 파일을 바이트로 변환해 첨부함
* 클라이언트는 body에 첨부된 파일을 받은 후, 만약 html 파일이 참조하는 css, js 등의 파일이 있는 경우, 해당 경로로 다시 웹 서버에 요청을 전송
* 웹 서버는 요청을 받고, 다시 요청에 명시된 경로에 있는 파일을 반환 -> 즉 한 개의 html 파일을 렌더링 하기 위해 여러 개의 요청을 처리함

* 웹 서버가 반환하는 response status에 따른 웹 브라우저는 동적으로 반응함
  * 예를 들어, response status 302 FOUND는 요청한 resource가 Location 헤더에 명시된 위치로 이동됐음을 의미함. 따라서 웹 브라우저는 자동으로 웹 서버에 Location에 명시된 위치로 다시 요청을 전송함

</div>
</details>


