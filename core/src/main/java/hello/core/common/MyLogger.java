package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)// 진짜가 아닌 가짜 프록시가 싱글톤처럼 등록,사용이 된다.
//이 가짜 프록시 빈은 호출시 실제 MyLogger를 호출하는 위임 로직을 가지고 있다. 프록시는 진짜 빈을 찾는 법을 알고 있다.
// Provider나 Proxy나 중요한 아이디어는 진짜 객체 조회를 최대한 미루고, 지연처리를 한다는 점이다.
public class MyLogger {
    private String uuid;
    private String requestURL;


    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }
    public void log(String message){
        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);

    }

    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "]"  + "request scope bean create:" + this);
    }
    @PreDestroy // request 스코프는 디스트로이어 호출 된다
    public void close(){
        System.out.println("[" + uuid + "]"  + "request scope bean close:" + this);
    }
}
