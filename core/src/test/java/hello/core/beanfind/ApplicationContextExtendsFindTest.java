package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);


    @Test
    @DisplayName("부모타입으로 모두조회")
    void findBeanByAllParentType(){
        Map<String, DiscountPolicy> beans = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beans.size()).isEqualTo(2);
        for (String key : beans.keySet()) {
            System.out.println("key = " + key + "value"+ beans.get(key));

        }
    }

    @Test
    @DisplayName("Object 타입으로 모두조회")
    void findBeanByAllObjectiveParentType(){
        Map<String, Object> beans = ac.getBeansOfType(Object.class);
//        assertThat(beans.size()).isEqualTo(2);
        for (String key : beans.keySet()) {
            System.out.println("key = " + key + "value"+ beans.get(key));

        }
    }

    @Test
    @DisplayName("부모타입으로 조회시 자식이 둘 이상 있으면, 중복 오류가 나타난다.")
    void findBeanByParentType(){
//        DiscountPolicy bean = ac.getBean(DiscountPolicy.class);
        assertThrows(NoUniqueBeanDefinitionException.class, ()-> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모타입으로 조회시 자식이 둘 이상 있으면, 이름을 지정하면 된다")
    void findBeanByParentType2(){
        DiscountPolicy bean = ac.getBean("FixDiscountPolicy",DiscountPolicy.class);
        assertThat(bean).isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("부모타입으로 조회시 자식이 둘 이상 있으면, 아예 하위타입 지정도 가능,")
    void findBeanByParentType3(){
        DiscountPolicy bean = ac.getBean(FixDiscountPolicy.class);
        assertThat(bean).isInstanceOf(DiscountPolicy.class);
    }



    @Configuration
    static class TestConfig{

        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }


        @Bean
        public DiscountPolicy FixDiscountPolicy(){
            return new FixDiscountPolicy();
        }
    }

}
