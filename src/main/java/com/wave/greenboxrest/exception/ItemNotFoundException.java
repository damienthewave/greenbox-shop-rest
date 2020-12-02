package com.wave.greenboxrest.exception;

import javax.persistence.PersistenceException;

public class ItemNotFoundException extends PersistenceException {

    public ItemNotFoundException(Long itemId) {
        super("Item was not found. Id: " + itemId);
    }

}
