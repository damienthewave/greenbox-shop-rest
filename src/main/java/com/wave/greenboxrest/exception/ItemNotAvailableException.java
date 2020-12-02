package com.wave.greenboxrest.exception;

import javax.persistence.PersistenceException;

public class ItemNotAvailableException extends PersistenceException {

    public ItemNotAvailableException(Long id) {
        super("Item is not available. Id: " + id);
    }

}
