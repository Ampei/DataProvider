package com.dataiprovider.exceptions;

/**
 *
 * @author ignacioesmite
 */
public class DataProviderNotFoundException extends DataProviderBaseException {
    public DataProviderNotFoundException() {
        super();
    }

    public DataProviderNotFoundException(String msg) {
        super(msg);
    }
}
