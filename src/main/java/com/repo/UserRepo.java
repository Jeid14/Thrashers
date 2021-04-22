package com.repo;

import com.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
}
