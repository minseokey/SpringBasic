package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


//1. 인터페이스 InitializingBean , DisposableBean
//2. 빈 등록시 지정
//3. 어노테이션
public class NetworkClient  {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출,url = " + url);
        connect();
        call("초기화 연결 메시지");

    }

    public void setUrl(String url) {
        this.url = url;
    }

    @PostConstruct
    // 권장되는 방법, 생성직후에 실행
    public void connect(){
        System.out.println("connect = " + url);
    }
    public void call(String message){
        System.out.println("call = " + url + "message = " + message);
    }

    @PreDestroy
    //권장되는 방법, 바괴 직전에 실행
    public void disconnect(){
        System.out.println("close = " + url);
    }
    // initializingbean 세팅을 마치고 호출
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        connect();
//        call("초기화 연결");
//
//    }
//
    // disposablebean 종료직전에 호출
//    @Override
//    public void destroy() throws Exception{
//        disconnect();
//    }
}
