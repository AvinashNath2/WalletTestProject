package com.example.ewallet.service;

import java.util.List;

import com.example.ewallet.datatransferobject.UserDTO;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.models.User;


/**
 * Service for Users
 */
public interface UserService {

    /**
     * Save a user
     */
    UserDTO saveUser(User user);

    /**
     * Update a user
     */
    UserDTO updateUserInfo(User user, String emailId) throws Exception;

    /**
     * get list of users
     */
    List<User> getListOfALlUsers();

    /**
     * user account by account id
     */
    UserDTO getUserByAccountId(Long accountId) throws UserNotFoundException;
}