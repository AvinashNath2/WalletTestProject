package com.example.ewallet.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.example.ewallet.datatransferobject.UserDTO;
import com.example.ewallet.exceptions.SystemException;
import com.example.ewallet.mapper.UserObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ewallet.dataaccessrepository.UserRepository;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.models.User;
import com.example.ewallet.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO getUserByAccountId(Long accountId) throws UserNotFoundException {
        User user = userRepository.findById(accountId).orElseThrow(
                () -> new UserNotFoundException(String.format("user not found for id :'%d'", accountId)));
        return UserObjectMapper.doToDTO(user);
    }

    @Override
    @Transactional
    public UserDTO saveUser(User user) {
        if (validateEmail(user.getEmailId())) {
            Optional<User> oldUser = userRepository.findByEmailId(user.getEmailId());
            if (oldUser.isPresent()) {
            	throw new SystemException(HttpStatus.BAD_REQUEST.value(), "User Already exist for this email-Id : "+ user.getEmailId());
            }
            return UserObjectMapper.doToDTO(userRepository.save(user));
        }
        throw new SystemException(HttpStatus.BAD_REQUEST.value(), "Please enter valid email-Id");
    }

    private boolean validateEmail(String emailId){
        if(ObjectUtils.isEmpty(emailId)){
            throw new SystemException(HttpStatus.BAD_REQUEST.value(), "email-Id is mandatory");
        }
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(emailId).matches();
    }

    @Override
    @Transactional
    public UserDTO updateUserInfo(User user, String emailId) throws Exception {
        if (emailId != null) {
            try {
				User oldUserInfo = userRepository.findByEmailId(emailId).orElseThrow(
						()-> new UserNotFoundException(String.format("user not found for email-Id", toString())));
				//update user info
				oldUserInfo.setUserName(user.getUserName());

                return UserObjectMapper.doToDTO(userRepository.save(oldUserInfo));
            } catch (Exception e) {
                throw new Exception("Try again");
            }
        }
        throw new SystemException("user email-Id is mandatory");
    }

    @Override
    public List<User> getListOfALlUsers() {
        return Lists.newArrayList(userRepository.findAll());
    }

}