package com.mitrais.bootcamp.minibank.service.base;

import com.mitrais.bootcamp.common.dto.BaseServiceResponse;
import com.mitrais.bootcamp.common.dto.ResponseWrapper;
import com.mitrais.bootcamp.minibank.domain.ErrorDetail;
import com.mitrais.bootcamp.minibank.exceptions.MiniBankException;

public class ServiceTemplate {

    private ServiceTemplate() {

    }

    public static <T extends BaseServiceResponse> void execute(ResponseWrapper<T> result, ServiceCallback callback) {
        try {
            callback.checkParameter();
            callback.process();
        }catch (MiniBankException e){
            fillFailResult(result, e);
        }catch (Exception e){
            fillFailResult(result, null);
        }
    }

    public static <T extends BaseServiceResponse> void fillFailResult(ResponseWrapper<T> result,
                                          MiniBankException e) {
        result.setSuccess(false);
        if(e != null){
            result.setErrorDetail(e.getErrorDetail());
        }else {
            result.setErrorDetail(new ErrorDetail("500", "unknown error"));
        }
    }

}