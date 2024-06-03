package com.mitrais.bootcamp.minibank.repository;

import com.mitrais.bootcamp.common.dto.TransactionDTO;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private List<TransactionDTO> transactionList = new ArrayList<TransactionDTO>();

    public List<TransactionDTO> getAllTransaction(){
        return this.transactionList;
    }

    public void addNew(TransactionDTO transaction){
        this.transactionList.add(transaction);
    }

    public void addAll(List<TransactionDTO> transactions){
        this.transactionList.addAll(transactions);
    }

}