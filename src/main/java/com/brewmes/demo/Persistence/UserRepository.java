package com.brewmes.demo.Persistence;

import com.brewmes.demo.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Long> {

    public UserModel findByUsername(String username);
}