package hello.core.singletontest;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService sf1 = ac.getBean(StatefulService.class);
        StatefulService sf2 = ac.getBean(StatefulService.class);

        //A 10000원
        sf1.order("UserA", 10000);
        //B 20000원
        sf2.order("UserB", 20000);

        //A의 금액조회?? (Stateful)
        int price = sf1.getPrice();
        System.out.println("price = " + price);

        Assertions.assertThat(sf1.getPrice()).isEqualTo(20000);

    }



    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}
