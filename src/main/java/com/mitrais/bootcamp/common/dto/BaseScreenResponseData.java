/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.common.dto;

import com.mitrais.bootcamp.common.enums.ScreenCode;

public abstract class BaseScreenResponseData {

    private ScreenCode screenCode;

    public ScreenCode getScreenCode() {
        return screenCode;
    }

    public void setScreenCode(ScreenCode screenCode) {
        this.screenCode = screenCode;
    }
}