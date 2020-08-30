package com.example.ewallet.service;

import java.util.List;

import com.example.ewallet.datatransferobject.MoneyTransferDto;
import com.example.ewallet.datatransferobject.PassbookDTO;
import com.example.ewallet.datatransferobject.UserTransactionDTO;
import com.example.ewallet.exceptions.BalanceLowException;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.models.TransactionStatus;
import com.example.ewallet.models.UserTransaction;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Service for Transaction
 */
public interface TransactionService {

    /**
     * gets list of transactions by account id
     */
    PassbookDTO getPassbookDetail(Long accountId) throws UserNotFoundException;

    /**
     * reverse transaction for an account
     */
    MoneyTransferDto reverseTransaction(String transactionHash) throws UserNotFoundException;


    List<UserTransaction> getTransactionByStatus(TransactionStatus transactionStatus, Long userId) throws UserNotFoundException;

    /**
     * create transaction for an account
     */
    UserTransactionDTO createTransaction(UserTransactionDTO userTransactionDTO, Long userAccountId) throws BalanceLowException;

    /**
     * Transfer Money from one user to other
     */
    MoneyTransferDto transferMoneyFromOneUserToAnother(UserTransactionDTO walletDTO, Long toUserAccountId, Long fromUserAccountId) throws UserNotFoundException, BalanceLowException;
}