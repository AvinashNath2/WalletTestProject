package com.example.ewallet.walletapp.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.ewallet.WalletResponse;
import com.example.ewallet.datatransferobject.CoreResponseDTO;
import com.example.ewallet.datatransferobject.MoneyTransferDto;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ewallet.datatransferobject.UserTransactionDTO;
import com.example.ewallet.mapper.TransactionObjectMapper;
import com.example.ewallet.exceptions.BalanceLowException;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.service.TransactionService;

@Slf4j
@RestController
@RequestMapping("v1/transferTo")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/{id}")
	public ResponseEntity addMoney(@PathVariable("id") Long userAccountId, @RequestBody UserTransactionDTO transactionDTO) {
		UserTransactionDTO saved;
		try {
			saved = transactionService.createTransaction(transactionDTO, userAccountId);
		} catch (BalanceLowException e) {
			log.error("[addMoney] error occurred while getting user by Id: {}", userAccountId, e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("[addMoney] error occurred while getting user by Id: {}", userAccountId, e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserTransactionDTO>(saved, HttpStatus.CREATED);
	}

	@PostMapping("/{toUser}/from/{fromUser}")
	public ResponseEntity<?> transferMoney(@PathVariable(value = "toUser") Long toUserAccountId,
			@PathVariable("fromUser") Long fromUserAccountId, @RequestBody UserTransactionDTO walletDTO) {
		MoneyTransferDto transactionInfo;
		try {
			transactionInfo = transactionService.transferMoneyFromOneUserToAnother(walletDTO, toUserAccountId, fromUserAccountId);
		} catch (UserNotFoundException e) {
			log.error("[transferMoney] error occurred while transferring money to: {}", toUserAccountId, e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (BalanceLowException e) {
			log.error("[transferMoney] error occurred while transferring money to: {}", toUserAccountId, e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return CoreResponseDTO.buildWithSuccess(WalletResponse.SUCCESS200, transactionInfo);
	}
}
