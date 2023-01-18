
package scope;

        import org.junit.jupiter.api.Test;
        import org.springframework.boot.test.context.assertj.ApplicationContextAssert;
        import org.springframework.context.ApplicationContext;
        import org.springframework.context.annotation.AnnotationConfigApplicationContext;
        import org.springframework.context.annotation.Scope;

        import javax.annotation.PostConstruct;
        import javax.annotation.PreDestroy;

        import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProtypeTest {
    @Test
    void ProtypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtypeBean.class);

        System.out.println("ProtypeTest.ProtypeBeanFind1");
        ProtypeBean Bean1 = ac.getBean(ProtypeBean.class);
        System.out.println("ProtypeTest.ProtypeBeanFind2");
        ProtypeBean Bean2 = ac.getBean(ProtypeBean.class);

        System.out.println("Bean2 = " + Bean2);
        System.out.println("Bean1 = " + Bean1);

        assertThat(Bean1).isNotSameAs(Bean2);

        // 이렇게 종료 메소드를 클라이언트가 직접 처리해 주어야 한다.
        Bean1.destroy();
        Bean2.destroy();

        ac.close();
    }
    @Scope("prototype")
    static class ProtypeBean{
        @PostConstruct
        public void init(){
            System.out.println("protypeBean.init");

        }
        //프로토타입일때는 삭제 작업이 이루어지지 않는다. 그냥 만들고 버려진다. 스프링이 관리 X
        @PreDestroy
        public void  destroy(){
            System.out.println("prototypeBean.close");

        }
    }
}
