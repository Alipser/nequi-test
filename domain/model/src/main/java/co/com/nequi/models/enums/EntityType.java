package co.com.nequi.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum EntityType {
    FRANCHISE("FRANCHISE"),
    BRANCH("BRANCH"),
    PRODUCT("PRODUCT");

    private final String value;

}