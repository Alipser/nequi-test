package co.com.nequi.models.product;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Product {
    String id;
    String name;
    int stock;
    String branchId;
    String franchiseId;
    String entityType;
}
