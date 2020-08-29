package com.example.ewallet.service;

import java.util.List;

import com.example.ewallet.datatransferobject.UserDTO;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.models.User;


/** Service for Users */
public interface UserService {

	/**
	 * Save a user
	 */
	UserDTO save(User t) throws Exception;

	/**
	 * Update a user
	 */
	UserDTO update(User t, Long id) throws Exception;

	/**
	 * get list of users
	 */
	List<User> getList();

	/**
	 * user account by account id
	 */
	UserDTO getAccountById(Long accountId) throws UserNotFoundException;
}