# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was
를 참고하여 작성되었습니다.

## 프로젝트 학습 내용

+ lombok @Data
  + 종합 선물 세트 !
    + @Getter/@Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 다 들어있음
    + @EqualsAndHashCode
      + equals() 메소드와 hashCode() 메소드 자동 생성
        + hashCode() : 런타임 중 객체의 유일한 integer 값을 반환하는 함수
    + @RequiredArgsConstructor
      + 초기화 되지 않은 final 필드나 @NonNull 어노테이션이 붙은 필드에 대해 생성자를 만들어 준다.
      + @AllArgsConstructor, @NoArgsConstructor
+ Session method
  + session.setAttribute(이름, 값)
  + session.getAttribute(이름)
    + 리턴 타입이 Object이므로 형 변환이 필요
+ Thymeleaf
```HTML
    <li><a th:if="${isLogin}" th:text="${userName}"></a></li>
    <li><a href="user/login.html" role="button" th:if="${!isLogin}">로그인</a></li>
    <li><a href="user/form.html" role="button" th:if="${!isLogin}">회원가입</a></li>
```
  + html 태그로 불리안, 변수 가능
  + model 객체를 통해 삽입
```java
    model.addAttribute("userName", loginUser.getName());
    model.addAttribute("isLogin", true);
```
+ gradle dependencies
  + runtimeOnly / compileOnly
    + compileOnly - 컴파일 시점에 꼭 필요한 라이브러리
    + runtimeOnly - 컴파일 시점에는 필요 없지만 실행 시점에는 꼭 필요한 라이브러리


    
    
    
    

