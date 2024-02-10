package qp.official.qp.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class LocalStackS3Config {
    private static final DockerImageName LOCALSTACK_IMAGE = DockerImageName.parse("localstack/localstack");

    @Bean(initMethod = "start", destroyMethod = "stop")
    public LocalStackContainer localStackContainer(){
        return new LocalStackContainer(LOCALSTACK_IMAGE)
            .withServices(Service.S3);
    }

    @Bean
    public AmazonS3 amazonS3(LocalStackContainer localStackContainer){
        AmazonS3 amazonS3 = AmazonS3ClientBuilder
            .standard()
            .withEndpointConfiguration(localStackContainer.getEndpointConfiguration(Service.S3))
            .withCredentials(localStackContainer.getDefaultCredentialsProvider())
            .build();

        amazonS3.createBucket("test");
        return amazonS3;
    }
}
