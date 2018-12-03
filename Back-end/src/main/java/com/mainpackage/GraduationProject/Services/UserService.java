package com.mainpackage.GraduationProject.Services;

import org.springframework.data.repository.CrudRepository;

import com.mainpackage.GraduationProject.Model.User;


public interface UserService extends CrudRepository<User, Integer> {

	
	Iterable<User> findByEmailAndPassword (String email,String password);
        Optional<User> findByEmail (String email);

 }
