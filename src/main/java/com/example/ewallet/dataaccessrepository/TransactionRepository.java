package com.example.ewallet.dataaccessrepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.ewallet.models.UserTransaction;

public interface TransactionRepository extends CrudRepository<UserTransaction, Long> {

	/**
	 * gets transaction by id
	 */
	@Query(nativeQuery = true, value = "select * from transaction where transaction_reference = ?")
	Optional<UserTransaction> getTransactionyId(Long transactionId);

	/**
	 * gets balance in account
	 */
	@Query(nativeQuery = true, value = "select ifnull(sum(amount),0.00) from transaction where user_account_id = ?")
	BigDecimal getBalance(Long accountId);

	/**
	 * gets list of transactions of particular account
	 */
	@Query(nativeQuery = true, value = "select * from transaction where user_account_id = ?")
	List<UserTransaction> getTransactionsForUser(Long accountId);
}
