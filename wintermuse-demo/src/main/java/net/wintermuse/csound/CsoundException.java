package net.wintermuse.csound;

/**
 * Created by oscii on 31/07/14.
 */
public class CsoundException extends Exception {
    public CsoundException() {
    }

    public CsoundException(String message) {
        super(message);
    }

    public CsoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsoundException(Throwable cause) {
        super(cause);
    }

    public CsoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
