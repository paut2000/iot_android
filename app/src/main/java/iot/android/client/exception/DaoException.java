package iot.android.client.exception;

import java.io.IOException;

public class DaoException extends IOException {

    public DaoException(String message) {
        super("DaoException: " + message);
    }

}
