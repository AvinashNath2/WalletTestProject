package com.example.ewallet.service;

import java.util.List;

import com.example.ewallet.datatransferobject.UserTransactionDTO;
import com.example.ewallet.exceptions.BalanceLowException;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.models.UserTransaction;

/** Service for Transaction */
public interface TransactionService {

	/**
	 * gets list of transactions by account id
	 */
	List<UserTransactionDTO> transactionsByUserAccountID(Long accountId) throws UserNotFoundException;

	/**
	 * create transaction for an account
	 */
	UserTransactionDTO createTransaction(UserTransaction txn) throws BalanceLowException;

	/**
	 * Transfer Money from one user to other
	 */
	List<UserTransactionDTO> transferMoneyFromOneUserToAnother(UserTransactionDTO walletDTO, Long toUserAccountId, Long fromUserAccountId) throws UserNotFoundException,BalanceLowException;
}