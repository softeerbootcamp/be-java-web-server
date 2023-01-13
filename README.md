# java-was-2022

Java Web Application Server 2022

## 프로젝트 정보

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was
를 참고하여 작성되었습니다.

# 데모 영상

https://drive.google.com/file/d/1Z-KNZgC6X3H5bxXtWifqP7BtFPTPyL_M/view?usp=share_link

# 디렉토리 구조

<img width="401" alt="스크린샷 2023-01-13 오전 10 50 15" src="https://user-images.githubusercontent.com/80745404/212218564-944d1f8b-34ee-4cad-a424-5a71fa5ee0c9.png">

# Flow

1. 서버 시작
2. 요청 발생 시 HttpRequest, HttpResponse 객체 생성

- 이때 HttpRequset는 요청받은 request를 바탕으로 생성하고, HttpResponse는 초기화만 진행

3. ControllerFactory에서 어느 Controller를 사용할지 판단

- 각 컨트롤러마다 자신이 해야하는 일의 path 리스트가 들어있고, 그 리스트의 items와 요청한 경로를 비교하여 매칭판단
- 매칭된 컨트롤러가 없을 시 httpResponse.do404() 호출
    - do404() : 404 Error Page

      <img width="200" alt="스크린샷 2023-01-13 오전 2 50 31" src="https://user-images.githubusercontent.com/80745404/212219318-05b4bdbf-28be-401c-8b10-636fbfc085c2.png">

4. 매칭된 Controller의 service() 호출
5. HttpMethod에 따라 각 역할을 하는 함수 호출 ex) doGet()

- Resource 파일일 경우 파일을 load 하고 httpResponse.forward()를 통해 전달 후 Response 작성
    - 파일이 존재하지 않을 경우 httpResponse.do404() 호출
- 특정 기능 요청시 ex) /user/create
    - 회원 정보를 db에 저장하는 회원가입 로직 수행후 httpResponse.sendRedirect()를 통해 Response 작성

# 공부내용

## 서블릿이란?

- Dynamic Web Page를 만들 대 사용되는 자바 기반의 웹 어플리케이션 프로그래밍 기술
- 웹 요청과 응답의 흐름을 간단한 메서드 호출만으로 체계적으로 다룰 수 있게 해주는 기술

## 서블릿의 특징

- Client의 Request에 대해 동적으로 작동하는 웹 애플리케이션 컴포넌트
- HTML을 사용하여 Response
- JAVA의 스레드를 이용하여 동작
- MVC 패턴에서의 컨트롤러로 이용됨
- HTTP 프로토콜 서비스를 지원하는 javax.servlet.HttpServlet 클래스를 상속받음
- UDP보다 속도가 느림
- HTML 변경 시 Servlet을 재 컴파일해야 하는 단점이 있다.

## 클라이언트의 요청으로부터 시작되는 웹서버의 처리 순서

1. 클라이언트가 웹 브라우저에서 서비스 요청. 이때, HTTP 프로토콜 기반으로 요청정보가 만들어져 웹 서버에 전달
2. 웹 서버는 클라이언트로부터 전달받은 요청정보의 URI를 살펴보고, 서블릿이라면 서블릿 컨테이너에 처리를 넘김
3. 서블릿 컨테이너는 요청받은 서블릿 클래스 파일을 찾아서 실행
4. 실행할 때 첫 순서는 최초의 요청인지 파악. 최초의 요청이라면 메모리에 로딩 후 객체를 생성하고 init() 메소드 호출
5. init() 메소드 실행이 끝난 다음에는 최초의 요청이든지 그렇지 않든지 서블릿 실행 요청이 들어올 때마다 실행되는 작업으로,
   서블릿 컨테이너는 HttpServletRequest 와 HttpServletResponse 객체 생성
6. service() 메소드 호출. 이때, 앞에서 생성한 HttpServletRequest 와 HttpServletResponse 객체는 클라이언트에게 보내는 응답정보를 처리할 목적으로 생성
7. service() 매소드 완료시 클라이언트에게 응답을 보내고 서버에서 실행되는 프로그램은 완료됨. 이때 두 객체 모두 소멸

## Request HTTP 메시지

1. StartLine

- HTTP Method : GET, POST, PUT, DELETE 등
- Request Target
- HTTP Version

2. Header

- Host : 요청하려는 서버 호스트 이름과 포트번호
- User-agent : 클라이언트 프로그램 정보 (이 정보를 통해 서버는 클라이언트 프로그램에 맞는 최적의 데이터 전송 가능)
- Referer : 현재 페이지에 요청한 이전 페이지의 uri 정보
- Accept : 클라이언트가 처리 가능한 미디어 타입 종류 나열
- If-Modified-Since : 여기에 쓰여진 시간 이후로 변경된 리소스 취득, 페이지 수정 시 최신 페이지로 교체
- Authorization : 인증 토큰을 서버로 보낼 때 쓰이는 Header
- Origin : 서버로 Post 요청을 보낼 때 요청이 어느 주소에 시작되었는지 나타내는 값. 이 값으로 요청을 보낸 주소와 받는 주소가 다르면 CORS 에러 발생
- Cookie : 쿠키 값이 key-value 로 표현됨

3. Body

## 테스트코드 (assertThat 관련)

### isEqualTo

결과가 기대하는 값과 일치하는지 확인

```java
class test {
    @Test
    void checkNumEqual() {
        int num = 1;
        assertThat(num).isEqualTo(1);
    }
}
```

### assertThatThrownBy

Exception이 발생하는 케이스를 테스트 할 때 사용

```java
class test {
    @Test
    void charAt_범위_밖() throws Exception {
        // given
        String input = "abc";

        // when, then
        assertThatThrownBy(() -> input.charAt(input.length()))
                .isInstanceOf(StringIndexOutOfBoundsException.class)
                .hasMessageContaining("String index out of range")
                .hasMessageContaining(String.valueOf(input.length()));
    }
}
```

- Params : exception이 발생하는 메서드를 람다식으로 입력
- Chaining
    - isInstanceOf : 예상되는 exception을 .class 형태로 입력
    - hasMessageContaining : exception 메시지에 입력되는 문자열이 포함되는지 확인

## Exception

- ArrayIndexOutOfBoundException
    - 배열의 크기보다 크거나 음수 인덱스에 대한 요청이 있을시 발생하는 런타임 에러
- NullPointerException
    - null 값을 가지는 객체를 참조하려고 사용할 시 발생하는 런타임 에러

## Git

### 원격 저장소에서 업데이트 받아오기

- Alice가 로컬 저장소의 main 브랜치를 업데이트 하려한다.
- 며칠 전 Bob이 원본 원격 저장소 alice/project에 커밋을 추가했다.
- 원본 저장소 alice/project에 추가된 3개의 커밋을 로컬 저장소로 가져와 업데이트하고자 한다.

#### fetch

    fetch를 실행하면 원격 저장소의 내용을 로컬 저장소로 가져오며, 임시로 FETCH_HEAD 라는 이름의 브랜치를 만든다.
    merge로 가져온 내용을 main 브랜치에 merge 한다. 만약 충돌 발생 시 직접 파일을 수정해야한다.

#### pull

    fetch와 merge를 연달아 진행한다.

## Collection.singletonList()

- 전달 인자로 들어온 객체만 포함하는 불변의 리스트를 반환하는 메서드
- Arrays.asList()를 사용하는 경우
    - 리스트 요소의 속성이 변경되어야 할 때
    - Arrays.asList()로 만들어진 리스트는 추가, 삭제가 되지는 않지만 요소의 속성은 변경이 가능
- Collections.singletonList()를 사용하는 경우
    - 리스트 요소의 주소 값 뿐만 아니라 속성 또한 불변어야 함을 보장해야 할 때 사용
    - Arrays.asList()와 달리 항상 크기가 1 (크기가 1로 항상 고정)
    - Collections.singletonList()로 만들어진 리스트는 모든 변경이 불가능
    - 속도 측면에서 가변인자 -> 배열변환의 과정이 없기 때문에 빠르고, 메모리 측면에서 효율적

## Logging

### 로깅을 하는 이유

로깅 : 시스템이 동작할 때 시스템의 상태 및 동작 정보를 시간 경과에 따라 기록하는 것을 의미함.  
로깅을 통해 개발 과정 혹은 개발 후에 발생할 수 있는 예상치 못한 애플리케이션의 문제를 진단할 수 있고,  
다양한 정보를 수집할 수 있다. 하지만 로깅을 하는 단계에서 적절한 수준의 로그 기록 기준을 잡지 못하면  
방대한 양의 로그 파일이 생성되거나 의미 없는 로그가 쌓이는 경우가 발생할 수 있다.

### 로그 레벨

Logback은 5단계의 로그 레벨을 가진다.  
심각도 수준은 Error > Warn > Info > Debug > Trace 이다.

- ⛔️ `Error` : 예상치 못한 심각한 문제가 발생하는 경우, 즉시 조취를 취해야 할 수준의 레벨
- ⚠ ️`Warn` : 로직 상 유효성 확인, 예상 가능한 문제로 인한 얘외 처리, 당장 서비스 운영에는 영향이 없지만 주의해야할 부분
- ✅ `Info` : 운영에 참고할만한 사항, 중요한 비즈니스 프로세스가 완료됨
- ⚙️ `Debug` : 개발 단계에서 사용하며, `SQL` 로깅이 가능
- 📝 `Trace` : 모든 레벨에 대한 로깅이 추적되므로 개발 단계에서 사용함

`Debug`, `Trace`레벨의 경우 많은 양의 로그가 쌓이게 된다. 그렇기 때문에 중요하지 않은 정보는  
`Debug` 이하로 설정하고 로깅을 하지 않는 편이 좋다.

> `Debug`, `Trace` 레벨의 로깅은 개발 단계에서만 사용하고 배포 단계에서 사용하지 말자.


## Thread
### Process란?
프로세스란 단순히 실행중인 프로그램이라고 할 수 있다.  
사용자가 작성한 프로그램이 운영체제에 의해 메모리 공간을 할당받아 실행 중인 것을 말함.  
프로그램에 사용되는 데이터와 메모리 등의 자원 그리고 스레드로 구성됨.

### Thread란?
프로세스 내에서 실제로 작업을 수행하는 주체.  
모든 프로세스에는 최소 1개 이상의 스레드가 존재하여 작업을 수행.
두개 이상의 스레드를 가지는 프로세스를 멀티스레드 프로세스라고 말함.

### Thread의 생성과 실행
자바에서 스레드를 생성하는 방법에는 두 가지 방법이 있다.
1. Runnable 인터페이스를 구현 (implements)
2. Thread 클래스 상속 (extends)

두 방법 모두 스레드를 통해 작업하고 싶은 내용을 run() 메서드에 작성하면 된다.

```java
class ThreadWithClass extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(getName()); 
            try {
                Thread.sleep(1);          
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ThreadWithRunnable implements Runnable {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName()); 
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Thread01 {
    public static void main(String[] args) {
        ThreadWithClass thread1 = new ThreadWithClass();       
        Thread thread2 = new Thread(new ThreadWithRunnable()); 
        
        thread1.start(); 
        thread2.start(); 
    }
}
```
<img width="274" alt="스크린샷 2023-01-13 오후 12 28 12" src="https://user-images.githubusercontent.com/80745404/212230509-a7619ac3-8e73-4833-9afd-a17304974c4d.png">

### 스레드의 우선순위
자바에서 각 스레드는 우선순위에 관한 자신만의 필드가 있다.  
이러한 우선순위에 따라 특정 스레드가 더 많은 시간 동안 작업을 할 수 있도록 설정할 수 있다.  
getPriority(), setPriority() 메서드를 통해 스레드의 우선순위를 반환하거나 변경할 수 있다.  
스레드의 우선순위가 가질 수 있는 범위는 1 ~ 10 까지이며, 숫자가 높을수록 우선순위가 높아진다.  
스레드의 우선순위는 해당 스레드를 생성한 스레드의 우선순위를 상속받게 된다.
