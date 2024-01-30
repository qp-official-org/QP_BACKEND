package qp.official.qp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import qp.official.qp.service.TokenService.TokenService;
import qp.official.qp.service.TokenService.TokenServiceImpl;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:secrets.properties", ignoreResourceNotFound = true),
})
public class SecretConfig {
    @Value("${JWT_SECRET}")
    private String secretKey;

    @Bean
    public TokenService tokenService() {
       return new TokenServiceImpl(secretKey) {};

    }
}
