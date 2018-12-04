package com.mainpackage.GraduationProject.Controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.ValidationException;

import org.apache.tomcat.util.http.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.mainpackage.GraduationProject.util.Constants;
import com.mainpackage.GraduationProject.util.ErrorMessage;

@RestController

public class UserController {
	@Autowired
UserService userservice;
Constants parameters;

@PostMapping("/register")
String create(@RequestParam(parameters.Email) String email,@RequestParam("name") String name,@RequestParam("password")String password,@RequestParam("type") int type ) 
{
	
	Optional<User> usercopy = userservice.findByEmail(email);
    
     if (email==null )
	{
		throw new ValidationException("invalid email");
	}
    
     else if(usercopy.isPresent())   // to check that email is exist before or not
    {
    	throw new DataIntegrityViolationException ("this mail already exist") ;
    }
    
	else if(password==null||password.length()<8||password.length()>15||password.contains(" "))
	{	
		throw new ValidationException("invalid password");	
	}
	else if(name==null)
	{	
		throw new ValidationException("invalid name");	
	}
	else if(type>3 || type<1)
	{	
		throw new ValidationException("invalid type");	
	}
	
	else
		return("success");
		//return userservice.save(user);
	
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

@GetMapping("/user/mail")
public User retrieveUser (@RequestParam("email") String email)
{
	Optional<User> user = userservice.findByEmail(email);
    if(user.isPresent())
    {
    	System.out.println("not valid");
    }
    else if (!user.isPresent())
    {
    	System.out.println(" valid");
    	throw new NoSuchElementException ("not found") ;
    	}
return user.get();
}



@GetMapping("/user/login")
	Iterable<User> findByQuery(@RequestParam("email") String email,@RequestParam("password") String password)
	{
	return userservice.findByEmailAndPassword(email, password);
	}



	
}
