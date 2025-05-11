package co.com.nequi.models.franchise;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Franchise {
    String id;
    String name;
    String entityType;
}
