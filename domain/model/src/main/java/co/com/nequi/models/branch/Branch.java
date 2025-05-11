package co.com.nequi.models.branch;

import lombok.Builder;
import lombok.Value;
@Value
@Builder(toBuilder = true)
public class Branch {
    String id;
    String name;
    String franchiseId;
    String entityType;
}
