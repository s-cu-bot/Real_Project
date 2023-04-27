[스프링 시큐리티 기능구현] 
------
- maven 4.0.0
- Spring Boot 3.0.6 (Java 17)
- Spring Security
- Spring Data JPA
- Thymeleaf
- Spring MVC
- MySQL 8.0.33

------
**[path : example\demo\]**
<pre><code> [AuthenticationProviderConfig.java]

- 인증 요청을 처리하는데 필요한 구성요소 관리
- SecurityFilterChain 설정
</pre></code>

<pre><code> [MainController.java]

- 웹 요청을 처리하고 적절한 뷰를 반환하는 컨트롤러
</pre></code>

<pre><code> [SecurityConfig.java]

- 웹 애플리케이션의 보안 설정 구성
- SecurityFilterChain 사용
</pre></code>

<pre><code> [SpringSecurityApplication.java]

- Spring Application 구성 및 실행
</pre></code>

------
**[path : resources\templates\]**
<pre><code>[index] / 메인화면
[login/index] / 로그인화면
[user/index] / user화면
</pre></code>

------
**[path : resources\templates\]**
<pre><code>[index] / 메인화면
[login/index] / 로그인화면
[user/index] / user화면
</pre></code>

-------
**[path : resources\application.properties\]**
<pre><code> mysql 연동
</pre></code>

-------
참고 : https://github.com/Youngerjesus/spring-security/blob/master/README.md
