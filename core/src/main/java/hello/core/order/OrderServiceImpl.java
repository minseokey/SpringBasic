package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// 파이널 붙은 필드를 추가한 생성자를 만들어준다, 빈 생성자는 @NoArgsConstructor
// 거기다가 생성자가 한개면 @Autowired 안써줘도 되므로 이렇게만 하면 생성자 주입 끝
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{


    // 3. 필드 주입, 권장되지 않습니다. 테스트코드 아님 컨피그레이션 수동으로 할떄 정도?
    // @Autowired private  MemberRepository memberRepository;
    // @Autowired private  DiscountPolicy discountPolicy;


    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 지금 추상과 구체를 모두 의존중이다... 아주 안좋아
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 1.생성자 주입법, 빈 등록과 동시에 의존성 주입, 불변, 필수적인 의존관계 주입

    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    // 2.수정자 주입법, 빈 등록 이후에 일어난다. 선택적 의존성 주입 가능 필수 아니면 @Autowired(required = false)
    // 변경 가능성이 있는 빈을 의존성 주입할때 사용
    // 이거와 비슷하게 일반 메서드에다가도 그냥 @Autowired를 사용 가능.
    // 생성자도 있고 수정자도 있으면 생성자 먼저 일어나고, 수정자에 해당하는 의존성 주입한다.(싱글톤이니까 무관)
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member,itemPrice);
//        멤버를 넘길래 아님 등급만 넘길래?

        return new Order (memberId,itemName,itemPrice,discountPrice);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
