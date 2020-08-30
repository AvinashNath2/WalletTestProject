package com.example.ewallet.walletapp.controller;
import com.example.ewallet.WalletResponse;
import com.example.ewallet.datatransferobject.CoreResponseDTO;
import com.example.ewallet.datatransferobject.MoneyTransferDto;
import com.example.ewallet.datatransferobject.PassbookDTO;
import com.example.ewallet.models.TransactionStatus;
import com.example.ewallet.models.UserTransaction;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ewallet.datatransferobject.UserTransactionDTO;
import com.example.ewallet.exceptions.BalanceLowException;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.service.TransactionService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("v1/transferTo")
public class TransactionController {

	private final TransactionService transactionService;

	@PostMapping("/{id}")
	public ResponseEntity<?> addMoney(@PathVariable("id") Long userAccountId, @RequestBody UserTransactionDTO transactionDTO) {
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

	@PostMapping("/reverse/{transactionHash}")
	public ResponseEntity<?> revertTransaction(@PathVariable("transactionHash") String reverseTransaction) {
		MoneyTransferDto saved;
		try {
			saved = transactionService.reverseTransaction(reverseTransaction);
		} catch (Exception e) {
			log.error("[revertTransaction] error occurred while reversing transaction Hash: {}", reverseTransaction, e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<MoneyTransferDto>(saved, HttpStatus.CREATED);
	}

	@PostMapping("/inquiry")
	public ResponseEntity<?> inquiryTransaction(@RequestParam("transactionStatus") TransactionStatus transactionStatus, @RequestHeader("userId") Long userId) {
		List<UserTransaction> transactions;
		try {
			transactions = transactionService.getTransactionByStatus(transactionStatus, userId);
		} catch (Exception e) {
			log.error("[inquiryTransaction] error occurred while reversing transaction Hash: {}", transactionStatus, e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<UserTransaction>>(transactions, HttpStatus.CREATED);
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
