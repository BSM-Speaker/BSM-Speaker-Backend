package bsm.speaker.global.error.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnAuthorizedException extends HttpError {

    private final int statusCode = 401;
    private String message = "UnAuthorized";

    public UnAuthorizedException(String message) {
        this.message = message;
    }
}