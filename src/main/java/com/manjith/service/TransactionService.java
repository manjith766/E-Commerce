package com.manjith.service;

import com.manjith.entity.Order;
import com.manjith.entity.Seller;
import com.manjith.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Order order);

    List<Transaction>getTransactionsBySellerId(Seller seller);
    List<Transaction>getAllTransactions();
}
