package app.exception;

public class ElementAlreadyUsed extends RuntimeException {
    public ElementAlreadyUsed() {
    }

    public ElementAlreadyUsed(String message) {
        super(message);
    }

    public ElementAlreadyUsed(String message, Throwable cause) {
        super(message, cause);
    }

    public ElementAlreadyUsed(Throwable cause) {
        super(cause);
    }

    public ElementAlreadyUsed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
