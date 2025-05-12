package co.com.nequi.dynamo.repository;

import co.com.nequi.dynamo.entity.RegisterDynamo;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
@Slf4j
public class RegisterRepository {

    private final DynamoDbEnhancedAsyncClient enhancedClient;
    private final ObjectMapper mapper;

    public RegisterRepository(@Qualifier("getEnhanceAsyncClient") DynamoDbEnhancedAsyncClient enhancedClient, ObjectMapper mapper) {
        this.enhancedClient = enhancedClient;
        this.mapper = mapper;
    }

    @Value("${aws.dynamo.table-name}")
    private String tableName;

    private DynamoDbAsyncTable<RegisterDynamo> getTable() {
        return enhancedClient.table(tableName, TableSchema.fromBean(RegisterDynamo.class));
    }

    public Mono<RegisterDynamo> save(RegisterDynamo entity) {
        return Mono.fromFuture(() -> getTable().putItem(entity))
                .doOnSuccess(unused -> log.info("{} saved: {}", entity.getEntityType(), entity))
                .doOnError(error -> log.error("Error saving Franchise: {}", error.getMessage(), error))
                .thenReturn(entity);
    }


}
