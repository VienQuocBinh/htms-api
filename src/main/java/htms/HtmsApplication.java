package htms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaRepositories
@EnableJpaAuditing
@AutoConfiguration
public class HtmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtmsApplication.class, args);
    }

}
