package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.RoleRepository;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;
import com.adaptivelearning.server.payload.ApiResponse;
import com.adaptivelearning.server.payload.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;

import javax.validation.Valid;

@RestController
@RequestMapping(Mapping.BASE_AUTH)
public class UserController {

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

    @GetMapping(Mapping.LOGIN)
    public User authenticateUser(@Valid @RequestParam(Param.EMAIL) String email,
                                 @Valid @RequestParam(Param.PASSWORD) String password) {
        User user = userRepository.findByEmail(email);

        //Do like this instead of optional
        if (user == null)
            throw new RestClientResponseException("User present", 300, "Unregistered", HttpHeaders.EMPTY, null, null);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        user.setToken(jwt);

        userRepository.save(user);

        return user;
    }

    @GetMapping(Mapping.LOGOUT)
    public void KickOutUser(@RequestParam(Param.ACCESSTOKEN) String token){
        if (!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Not found token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!tokenProvider.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        User user = userRepository.findByToken(token).get();
        user.setToken("");
        userRepository.save(user);
    }

    @SuppressWarnings("unchecked")
    @PostMapping(Mapping.REGISTER)
    public void registerUser(@Valid @RequestParam(Param.EMAIL) String email,
                                          @Valid @RequestParam(Param.NAME) String name,
                                          @Valid @RequestParam(Param.PASSWORD) String password,
                                          @Valid @RequestParam(Param.TYPE) int type) {
        if (userRepository.existsByEmail(email))
            throw new RestClientResponseException("Email is used", 300, "Unregistered", HttpHeaders.EMPTY, null, null);

        if (type < 1 || type > 3)
            throw new RestClientResponseException("type not allowed", 500, "NotAllowed", HttpHeaders.EMPTY, null, null);

        // Creating user's account
        User user = new User(name, email, password, type);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

//        return ResponseEntity.ok().body(new ApiResponse(200, "User registered successfully"));
    }
}
