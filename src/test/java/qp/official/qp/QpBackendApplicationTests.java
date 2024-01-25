package qp.official.qp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QpBackendApplicationTests {
    @Value("${JWT_SECRET}")
    private String secretKey;

    @Test
    void contextLoads() {
        System.out.println("secretKey: " + secretKey);
    }

}
