package com.example.ewallet.dataaccessrepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.ewallet.models.UserTransaction;

public interface UserTransactionRepository extends CrudRepository<UserTransaction, Long> {

	/**
	 * gets transaction by id
	 */
	@Query(nativeQuery = true, value = "select * from transaction where transaction_reference = ?")
	Optional<UserTransaction> getTransactionyId(Long transactionId);

	/**
	 * gets list of transactions of particular account
	 */
//	List<UserTransaction> findByTransactionFromOrTransactionTo(Long accountId);
	List<UserTransaction> findByTransactionFrom(Long accountId);
}
