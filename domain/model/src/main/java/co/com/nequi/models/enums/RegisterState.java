package co.com.nequi.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RegisterState {

    ACTV("ACTV"), DELETD("DLTD");

    private final String value;
}
