package qp.official.qp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class QpBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(QpBackendApplication.class, args);
    }

}
