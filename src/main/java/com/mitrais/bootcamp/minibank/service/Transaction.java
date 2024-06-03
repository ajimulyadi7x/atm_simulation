package com.mitrais.bootcamp.minibank.service;

import com.mitrais.bootcamp.common.dto.BaseServiceResponse;
import com.mitrais.bootcamp.common.dto.ResponseWrapper;
import com.mitrais.bootcamp.common.dto.BaseTransactionRequest;

public interface Transaction {

    ResponseWrapper<BaseServiceResponse> execute(BaseTransactionRequest request);
}