package com.mitrais.bootcamp.minibank.service.base;

import com.mitrais.bootcamp.minibank.exceptions.MiniBankException;

public interface ServiceCallback {

    /**
     * check param
     */
    void checkParameter() throws MiniBankException;

    /**
     * process business logic
     */
    void process() throws MiniBankException;
}