package qp.official.qp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import qp.official.qp.service.JWTService;

@SpringBootTest
class QpBackendApplicationTests {
    @Value("${JWT_SECRET}")
    private String secretKey;

    @Autowired
    private JWTService jwtService;

    @Test
    void contextLoads() {
        System.out.println("secretKey: " + secretKey);
        System.out.println("jwtService.getSecretKey: " + jwtService.getSecretKey());
    }

}
