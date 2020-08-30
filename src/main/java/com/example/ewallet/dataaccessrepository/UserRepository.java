package com.example.ewallet.dataaccessrepository;

import org.springframework.data.repository.CrudRepository;
import com.example.ewallet.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmailId(String userEmail);

}
