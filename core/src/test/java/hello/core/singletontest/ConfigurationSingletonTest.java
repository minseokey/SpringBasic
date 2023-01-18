package hello.core.singletontest;

import hello.core.AppConfig;
import hello.core.AutoAppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository ms3 = ac.getBean("memberRepository",MemberRepository.class);

        MemberRepository ms1 = memberService.getMemberRepository();
        MemberRepository ms2 = orderService.getMemberRepository();

        System.out.println("ms1 = " + ms1);
        System.out.println("ms2 = " + ms2);
        System.out.println("ms3 = " + ms3);

        assertThat(ms2).isSameAs(ms1).isSameAs(ms3);
    }

    @Test
    void configurationDeep(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig been = ac.getBean(AppConfig.class);

        System.out.println("been = " + been.getClass());
    }
}
