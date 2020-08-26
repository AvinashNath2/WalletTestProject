package com.example.ewallet.serviceImpl;

import java.util.List;

import com.example.ewallet.datatransferobject.UserDTO;
import com.example.ewallet.mapper.UserObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ewallet.dataaccessrepository.UserAccountRepository;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.models.User;
import com.example.ewallet.service.UserAccountService;
import com.google.common.collect.Lists;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	public UserDTO getAccountById(Long accountId) throws UserNotFoundException {
		User user = userAccountRepository.findById(accountId).orElseThrow(
				() -> new UserNotFoundException(String.format("user not found for id :'%d'", accountId)));
		return UserObjectMapper.doToDTO(user);
	}

	@Override
	@Transactional
	public UserDTO save(User user) throws Exception {
		if (user.getUserName() != null) {
			return UserObjectMapper.doToDTO(userAccountRepository.save(user));
		}
		throw new Exception("user name is mandatory");
	}

	@Override
	@Transactional
	public UserDTO update(User user, Long userAccountId) throws Exception {
		if (user.getUserName() != null) {
			user.setId(userAccountId);
			try {
				return UserObjectMapper.doToDTO(userAccountRepository.save(user));
			} catch (Exception e) {
				throw new Exception("Try again");
			}
		}
		throw new Exception("user name is mandatory");
	}

	@Override
	public List<User> getList() {
		return Lists.newArrayList(userAccountRepository.findAll());
	}

}