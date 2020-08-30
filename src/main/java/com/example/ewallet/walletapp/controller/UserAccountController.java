package com.example.ewallet.walletapp.controller;

import java.util.List;

import com.example.ewallet.WalletResponse;
import com.example.ewallet.datatransferobject.CoreResponseDTO;
import com.example.ewallet.datatransferobject.PassbookDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("v1/users")
@AllArgsConstructor
public class UserAccountController {

	private final UserService userService;

	private final TransactionService transactionService;

	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		List<User> users = userService.getList();
		return CoreResponseDTO.buildWithSuccess(WalletResponse.SUCCESS200, UserObjectMapper.doToDTOList(users));
	}

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
		UserDTO saved;
		try {
			saved = userService.save(UserObjectMapper.dtoToDO(userDTO));
		} catch (Exception e) {
			log.error("[createUser] error occurred while creating user : {}",e.getMessage(), e);
			return CoreResponseDTO.buildWithFailure(FAILURE400);
		}
		return CoreResponseDTO.buildWithSuccess(WalletResponse.USERCREATED201, saved);
	}


	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
		UserDTO user;
		try {
			user = userService.getAccountById(id);
		} catch (UserNotFoundException e) {
			log.error("[getUser] error occurred while getting user by Id: {}", id, e);
			return CoreResponseDTO.buildWithFailure(USERNOTFOUND404);
		}
		return CoreResponseDTO.buildWithSuccess(WalletResponse.SUCCESS200, user);

	}


	@PutMapping("/{id}")
	public ResponseEntity updateUser(@PathVariable("id") Long userAccountId,
			@RequestBody UserDTO userDTO) {
		UserDTO saved;
		try {
			saved = userService.update(UserObjectMapper.dtoToDO(userDTO), userAccountId);
		} catch (Exception e) {
			log.error("[updateUser] error occurred while getting user by Id: {}", userAccountId, e);
			return CoreResponseDTO.buildWithFailure(USERNOTFOUND404);
		}
		return CoreResponseDTO.buildWithSuccess(WalletResponse.SUCCESS200, saved);
	}


	@GetMapping("/{id}/passbook")
	public ResponseEntity getUserTransaction(@PathVariable("id") Long id) {
		PassbookDTO passbookDTO;
		try {
			passbookDTO = transactionService.getPassbookDetail(id);
		} catch (Exception e) {
			log.error("[getUserTransaction] error occurred while getting transaction by userId: {}", id, e);
			return CoreResponseDTO.buildWithFailure(USERNOTFOUND404);
		}
		return CoreResponseDTO.buildWithSuccess(WalletResponse.SUCCESS200, passbookDTO);
	}
}