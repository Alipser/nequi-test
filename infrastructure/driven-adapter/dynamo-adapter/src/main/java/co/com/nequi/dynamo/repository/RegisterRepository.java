package co.com.nequi.dynamo.repository;

import co.com.nequi.dynamo.entity.RegisterDynamo;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
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


}
