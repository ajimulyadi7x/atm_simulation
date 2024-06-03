
package com.mitrais.bootcamp.common.dto;

public class ATMSimulationResult<T> extends BaseResult {

    private T object;

    /**
     * Getter method for property object.
     *
     * @return property value of object
     */
    public T getObject() {
        return object;
    }

    /**
     * Setter method for property object.
     *
     * @param object value to be assigned to property object
     */
    public void setObject(T object) {
        this.object = object;
    }
}