package com.chernikov.simpler.v2.SimplerV2.repositories;

import org.springframework.data.repository.CrudRepository;

import com.chernikov.simpler.v2.SimplerV2.models.User;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {

}
