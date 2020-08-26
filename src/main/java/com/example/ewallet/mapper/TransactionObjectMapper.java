package com.example.ewallet.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ewallet.datatransferobject.UserTransactionDTO;
import com.example.ewallet.models.UserTransaction;

/** The Class to map Database Objects and Data Transfer Objects or vice-versa*/
public class TransactionObjectMapper {

	public static UserTransaction dtoToDO(UserTransactionDTO w) {
		UserTransaction userTransaction = new UserTransaction. TransactionBuilder().setUserAccount(w.getUserAccountId())
				.setAmount(w.getAmount()).setId(w.getId()).setDetails(w.getDetails())
				.build();
		return userTransaction;
	}

	public static UserTransactionDTO doToDTO(UserTransaction w) {
		UserTransactionDTO userTransactionDTO = new UserTransactionDTO.TransactionDTOBuilder()
				.setUserAccountId(w.getUserAccount().getId()).setAmount(w.getAmount()).setId(w.getId())
				.setDetails(w.getDetails()).setTransactionDate(w.getTransactionDate())
				.build();
		return userTransactionDTO;
	}

	public static List<UserTransactionDTO> doToDTOList(List<UserTransaction> userTransactions) {
		return userTransactions.stream().map(TransactionObjectMapper::doToDTO).collect(Collectors.toList());
	}
}