package bsm.speaker.global.error.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InternalServerException extends HttpError {

    private final int statusCode = 500;
    private String message = "Internal Server Error";

    public InternalServerException(String message) {
        this.message = message;
    }
}