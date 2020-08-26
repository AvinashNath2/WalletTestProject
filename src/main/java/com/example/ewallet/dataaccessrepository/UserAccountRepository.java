package com.example.ewallet.dataaccessrepository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.ewallet.models.User;

public interface UserAccountRepository extends CrudRepository<User, Long> {

	/**gets user by name
	 * @param name
	 * @return user account
	 */
	Optional<User> getByUserName(String name);
}
