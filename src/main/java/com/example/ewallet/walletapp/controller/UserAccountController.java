package com.example.ewallet.walletapp.controller;

import java.util.List;

import com.example.ewallet.WalletResponse;
import com.example.ewallet.datatransferobject.CoreResponseDTO;
import com.example.ewallet.datatransferobject.PassbookDTO;
import com.example.ewallet.exceptions.SystemException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ewallet.datatransferobject.UserDTO;
import com.example.ewallet.mapper.UserObjectMapper;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.models.User;
import com.example.ewallet.service.TransactionService;
import com.example.ewallet.service.UserService;

import javax.validation.Valid;

import static com.example.ewallet.WalletResponse.FAILURE400;
import static com.example.ewallet.WalletResponse.USERNOTFOUND404;
import static com.example.ewallet.walletapp.controller.TransactionController.ERROR400;

@Slf4j
@RestController
@RequestMapping("v1/users")
@AllArgsConstructor
public class UserAccountController {

	private final UserService userService;

	private final TransactionService transactionService;

	/**
	 * get all User
	 */
	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		List<User> users = userService.getListOfALlUsers();
		return CoreResponseDTO.buildWithSuccess(WalletResponse.SUCCESS200, UserObjectMapper.doToDTOList(users));
	}

	/**
	 * Create new User
	 */
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
		UserDTO saved;
		try {
			saved = userService.saveUser(UserObjectMapper.dtoToDO(userDTO));
		} catch (SystemException e) {
			log.error("[createUser] error occurred while creating user : {}",e.getMessage(), e);
			return CoreResponseDTO.buildWithFailureCodes(e.getMessage(), ERROR400, HttpStatus.BAD_REQUEST);
		}
		return CoreResponseDTO.buildWithSuccess(WalletResponse.USERCREATED201, saved);
	}


	/**
	 * Get User by Id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
		UserDTO user;
		try {
			user = userService.getUserByAccountId(id);
		} catch (UserNotFoundException e) {
			log.error("[getUser] error occurred while getting user by Id: {}", id, e);
			return CoreResponseDTO.buildWithFailureCodes(e.getMessage(), ERROR400, HttpStatus.BAD_REQUEST);
		}
		return CoreResponseDTO.buildWithSuccess(WalletResponse.SUCCESS200, user);

	}

	/**
	 * Update existing user by Email-Id: eg- Name in body
	 */
	@PutMapping("/{emailId}")
	public ResponseEntity updateUser(@PathVariable("emailId") String emailId,
			@RequestBody UserDTO userDTO) {
		UserDTO saved;
		try {
			saved = userService.updateUserInfo(UserObjectMapper.dtoToDO(userDTO), emailId);
		} catch (Exception e) {
			log.error("[updateUser] error occurred while getting user by Id: {}", emailId, e);
			return CoreResponseDTO.buildWithFailureCodes(e.getMessage(), ERROR400, HttpStatus.BAD_REQUEST);
		}
		return CoreResponseDTO.buildWithSuccess(WalletResponse.SUCCESS200, saved);
	}


	/**
	 * Get Passbook of that user
	 */
	@GetMapping("/{id}/passbook")
	public ResponseEntity getUserTransaction(@PathVariable("id") Long id) {
		PassbookDTO passbookDTO;
		try {
			passbookDTO = transactionService.getPassbookDetail(id);
		} catch (Exception e) {
			log.error("[getUserTransaction] error occurred while getting transaction by userId: {}", id, e);
			return CoreResponseDTO.buildWithFailureCodes(e.getMessage(), ERROR400, HttpStatus.BAD_REQUEST);
		}
		return CoreResponseDTO.buildWithSuccess(WalletResponse.SUCCESS200, passbookDTO);
	}
}