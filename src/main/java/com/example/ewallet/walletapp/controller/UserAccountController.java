package com.example.ewallet.walletapp.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ewallet.datatransferobject.UserTransactionDTO;
import com.example.ewallet.datatransferobject.UserDTO;
import com.example.ewallet.mapper.UserObjectMapper;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.models.User;
import com.example.ewallet.service.TransactionService;
import com.example.ewallet.service.UserAccountService;

@RestController
@RequestMapping("v1/users")
public class UserAccountController {

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private TransactionService transactionService;

	@GetMapping
	public ResponseEntity getAllUsers() {
		List<User> users = userAccountService.getList();
		return new ResponseEntity<List<UserDTO>>(UserObjectMapper.doToDTOList(users), HttpStatus.OK);
	}


	@PostMapping
	public ResponseEntity createUser(@RequestBody UserDTO userDTO) {
		UserDTO saved;
		try {
			saved = userAccountService.save(UserObjectMapper.dtoToDO(userDTO));
		} catch (Exception ex) {
			Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserDTO>(saved, HttpStatus.CREATED);
	}


	@GetMapping("/{id}")
	public ResponseEntity getUser(@PathVariable("id") Long id) {
		UserDTO user;
		try {
			user = userAccountService.getAccountById(id);
		} catch (UserNotFoundException ex) {
			Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);

	}

	@PutMapping("/{id}")
	public ResponseEntity updateUser(@PathVariable("id") Long userAccountId,
			@RequestBody UserDTO userDTO) {
		UserDTO saved;
		try {
			saved = userAccountService.update(UserObjectMapper.dtoToDO(userDTO), userAccountId);
		} catch (Exception ex) {
			Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserDTO>(saved, HttpStatus.OK);
	}

	@GetMapping("/{id}/transactions")
	public ResponseEntity getUserTransaction(@PathVariable("id") Long id) {
		List<UserTransactionDTO> allTransaction;
		try {
			allTransaction = transactionService.transactionsByUserAccountID(id);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<UserTransactionDTO>>(allTransaction, HttpStatus.OK);
	}
}