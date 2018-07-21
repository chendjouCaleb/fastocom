package app.exception;

public class ConstraintException extends RuntimeException {

    public ConstraintException() {
    }

    public ConstraintException(String message) {
        super(message);
    }

    public ConstraintException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstraintException(Throwable cause) {
        super(cause);
    }

    public ConstraintException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
