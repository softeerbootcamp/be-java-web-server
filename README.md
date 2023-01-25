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