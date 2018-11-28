package com.mainpackage.GraduationProject.Controller;

import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mainpackage.GraduationProject.Model.User;
import com.mainpackage.GraduationProject.Services.UserService;
import com.mainpackage.GraduationProject.util.ErrorMessage;

@RestController

public class UserController {
	@Autowired
UserService userservice;

@PostMapping("/register")
User create(@RequestBody User user) 
{
	if(user.getId() == 0 && user.getEmail() != null&& user.getName()!=null&&user.getPassword()!= null && user.getType() > 0 && user.getType() < 5)
	return userservice.save(user);
	else throw new ValidationException("Account not created");
	
}



@GetMapping("/user")
Iterable<User> read()
{
return userservice.findAll();	
}


@PutMapping("/user")
ResponseEntity<User>  update(@RequestBody User user)
{ 
	if (userservice.findById(user.getId()).isPresent())
	return  new ResponseEntity(userservice.save(user), HttpStatus.OK);
	else
		return   new ResponseEntity(user, HttpStatus.BAD_REQUEST); 
}

@DeleteMapping("/user/{id}")
void delete(@PathVariable Integer id)
{
	userservice.deleteById(id);
}


@GetMapping("/user/{id}")
Optional<User> findById(@PathVariable Integer id)
{
	 return userservice.findById(id);
}



@GetMapping("/user/login")
	Iterable<User> findByQuery(@RequestParam("email") String email,@RequestParam("password") String password)
	{
	return userservice.findByEmailAndPassword(email, password);
	}

	
}
