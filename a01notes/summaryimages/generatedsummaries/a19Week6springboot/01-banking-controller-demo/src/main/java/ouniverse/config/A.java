package ouniverse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ouniverse.kt.Sim;

@Configuration
public class A {

        @Bean
        public Sim getSim()
        {
            System.out.println("I am creating the object");
            return new Sim();
        };

}
