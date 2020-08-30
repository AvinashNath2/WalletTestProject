package com.example.ewallet.dataaccessrepository;

import java.util.List;
import java.util.Optional;

import com.example.ewallet.models.TransactionStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.ewallet.models.UserTransaction;

public interface UserTransactionRepository extends CrudRepository<UserTransaction, Long> {

	/**
	 * gets list of transactions of all particular account
	 */
	@Query(nativeQuery = true, value = "select * from transaction where transaction_from = :userId or transaction_to = :userId")
	List<UserTransaction> getTransactionByUserId(Long userId);

	Optional<UserTransaction> findByTransactionHash(String hash);

	/**
	 * gets list of transactions of all particular account
	 */
	@Query(nativeQuery = true, value = "select * from transaction where transaction_status = :transactionStatus and transaction_from = :userId or transaction_to = :userId")
	List<UserTransaction> getTransactionByTransactionStatus(Long userId, TransactionStatus transactionStatus);
}
