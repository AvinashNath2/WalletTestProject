package com.example.ewallet.dataaccessrepository;

import org.springframework.data.repository.CrudRepository;
import com.example.ewallet.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
