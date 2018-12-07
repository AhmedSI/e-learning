package com.login.GP.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.login.GP.Model.Parent_children_relation;
import com.login.GP.Model.Role;
import com.login.GP.Model.RoleName;
import com.login.GP.Model.User;
import com.login.GP.Repository.Parent_ChildRepository;
import com.login.GP.Repository.RoleRepository;
import com.login.GP.Repository.UserRepository;
import com.login.GP.Security.JwtTokenProvider;
import com.login.GP.exception.AppException;
import com.login.GP.payload.ApiResponse;
import com.login.GP.payload.JwtAuthenticationResponse;
import com.login.GP.payload.LoginRequest;
import com.login.GP.payload.SignUpRequest;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/auth")
public class AppControler {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Autowired
    Parent_ChildRepository parent_childRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getType());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.USER).orElseThrow (null);

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
        
    }
    
    @PostMapping("/AddChild")
    public ResponseEntity<?> registerChild(@RequestParam("Pid") long parentID,@RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("name") String name,@RequestParam("Password")String password,@RequestParam("type") int type ) {
    	if(userRepository.existsByUsername(username)) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
    	
    	if(userRepository.existsByUsername(username)) {
             return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                     HttpStatus.BAD_REQUEST);
         }

         if(userRepository.existsByEmail(email)) {
             return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                     HttpStatus.BAD_REQUEST);
         }
         
         if(type!= 4) {
             return new ResponseEntity(new ApiResponse(false, "In Valid Type!"),
                     HttpStatus.BAD_REQUEST);
         }

         // Creating user's account
         User user = new User(name, username,email,password,type);
        
         user.setPassword(passwordEncoder.encode(user.getPassword()));

         Role userRole = roleRepository.findByName(RoleName.USER).orElseThrow (null);

         user.setRoles(Collections.singleton(userRole));

         User insertedChild = userRepository.save(user);
         
         
         
         Parent_children_relation Parent_Child = new Parent_children_relation (insertedChild.getId(),parentID);
         
         parent_childRepository.save(Parent_Child);
         URI location = ServletUriComponentsBuilder
                 .fromCurrentContextPath().path("/api/users/{username}")
                 .buildAndExpand(insertedChild.getUsername()).toUri();

         return ResponseEntity.created(location).body(new ApiResponse(true, "Child Added successfully"));
         
    }
    }
