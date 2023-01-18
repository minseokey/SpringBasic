package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// 이러한 구조로 역할과 구현 구분 쉽게 가능
// 설정정보 클래스에 어노테이션 달아주기
@Configuration
public class AppConfig {

    //구현, 이부분만 교체를 해준다면 역할에서의 구현을 쉽게쉽게 변경 가능
    // 각 구성요소에 @Bean, 이러면 스프링 컨테이너에 등록이됨

    // 보기에는 memberService 만들때 memberRepository가 새로 만들어져 들어가는것 같아 보인다.
    // 하지만 스프링에서 다 하나로 유지시켜준다... 갓스프링
    // 수동빈이 자동빈을 오버라이딩 한다. 수동빈이 우선권을 지닌다. 하지만 스프링 부트에서는 오버라이딩이 막혀있다. 오류코드 참조하여 설정을 바꿔주어야함.
    @Bean
    public MemberRepository memberRepository() {
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy DiscountPolicy() {
        System.out.println("AppConfig.DiscountPolicy");
        return new RateDiscountPolicy();
    }


    // 역할
    @Bean
    public MemberService memberService() {
        System.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public OrderService orderService() {
        System.out.println("AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), DiscountPolicy());
    }


}
