package co.com.nequi.dynamo.entity;

import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
@Data
public class RegisterDynamo {

    @Getter(onMethod_ = {
            @DynamoDbPartitionKey,
            @DynamoDbAttribute("franchise")
    })
    private String franchise;

    @Getter(onMethod_ = {
            @DynamoDbSortKey,
            @DynamoDbAttribute("compositeKey")
    })
    private String compositeKey;

    @Getter(onMethod_ = {
            @DynamoDbAttribute("uniqueId"),
            @DynamoDbSecondaryPartitionKey(indexNames = {
                    "ix-uniqueId-entityType"
            })
    })
    private String uniqueId;

    @Getter(onMethod_ = {
            @DynamoDbAttribute("entityType"),
            @DynamoDbSecondarySortKey(indexNames = {
                    "ix-sucursal-entityType",
                    "ix-uniqueId-entityType"
            }),
            @DynamoDbSecondaryPartitionKey(indexNames = {
                    "ix-franchise-stock"
            })
    })
    private String entityType;

    @Getter(onMethod_ = @DynamoDbAttribute("name"))
    private String name;

    @Getter(onMethod_ = {
            @DynamoDbAttribute("stock"),
            @DynamoDbSecondarySortKey(indexNames = {
                    "ix-franchise-stock"
            })
    })
    private Integer stock;

    @Getter(onMethod_ = {
            @DynamoDbAttribute("sucursalId"),
            @DynamoDbSecondaryPartitionKey(indexNames = {
                    "ix-sucursal-entityType"
            })
    })
    private String sucursalId;

    @Getter(onMethod_ = @DynamoDbAttribute("createdAt"))
    private String createdAt;

    @Getter(onMethod_ = @DynamoDbAttribute("updatedAt"))
    private String updatedAt;

    @Getter(onMethod_ = @DynamoDbAttribute("state"))
    private String state;
}
