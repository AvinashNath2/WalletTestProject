package com.example.ewallet.walletapp.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	public ResponseEntity addMoney(@PathVariable("id") Long userAccountId, @RequestBody UserTransactionDTO walletDTO) {
		UserTransactionDTO saved;
		try {
			walletDTO.setUserAccountId(userAccountId);
			saved = transactionService.createTransaction(TransactionObjectMapper.dtoToDO(walletDTO));
		} catch (BalanceLowException ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserTransactionDTO>(saved, HttpStatus.CREATED);
	}

	@PostMapping("/{toUser}/from/{fromUser}")
	public ResponseEntity transferMoney(@PathVariable(value = "toUser") Long toUserAccountId,
			@PathVariable("fromUser") Long fromUserAccountId, @RequestBody UserTransactionDTO walletDTO) {
		List<UserTransactionDTO> transactionInfo;
		try {
			transactionInfo = transactionService.transferMoneyFromOneUserToAnother(walletDTO, toUserAccountId, fromUserAccountId);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (BalanceLowException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<UserTransactionDTO>>(transactionInfo, HttpStatus.OK);
	}
}
