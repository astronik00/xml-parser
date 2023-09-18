package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyRuntimeException extends RuntimeException {
    private String number;
    private String message;
}
