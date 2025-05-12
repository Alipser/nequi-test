package co.com.nequi.dynamo.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;
import java.time.Duration;

@Configuration
public class DynamoConfig {

    @Value("${aws.dynamo.endpoint}")
    @Setter
    String tableEndpoint;

    @Bean("getChain")
    public AwsCredentialsProviderChain getChain(){
        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .addCredentialsProvider(SystemPropertyCredentialsProvider.create())
                .addCredentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .addCredentialsProvider(ProfileCredentialsProvider.create())
                .addCredentialsProvider(ContainerCredentialsProvider.create())
                .addCredentialsProvider(InstanceProfileCredentialsProvider.create())
                .build();
    }

    @Bean
    public DynamoDbAsyncClient getDynamoAsyncClient(){
        return DynamoDbAsyncClient.builder()
                .credentialsProvider(getChain())
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create(tableEndpoint))
                .overrideConfiguration(ClientOverrideConfiguration.builder()
                        .apiCallTimeout(Duration.ofSeconds(25))
                        .apiCallAttemptTimeout(Duration.ofSeconds(12))
                        .build())
                .build();
    }

    @Bean("getEnhanceAsyncClient")
    public DynamoDbEnhancedAsyncClient getEnhanceAsyncClient(){
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(getDynamoAsyncClient())
                .build();
    }



}
