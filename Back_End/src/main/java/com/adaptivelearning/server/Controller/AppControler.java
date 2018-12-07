package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.Role;
import com.adaptivelearning.server.Model.RoleName;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.RoleRepository;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.payload.ApiResponse;
import com.adaptivelearning.server.payload.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;

import javax.validation.Valid;

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

    @GetMapping("/signin/email={email}/password={password}")
    public LoginResponse authenticateUser(@Valid @PathVariable("email") String email, @Valid @PathVariable("password") String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return new LoginResponse(300, null, null);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return new LoginResponse(200, jwt, user);
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/signup/username={username}/email={email}/name={name}/password={password}/type={type}")
    public ResponseEntity<?> registerUser(@Valid @PathVariable("username") String username, @Valid @PathVariable("email") String email, @Valid @PathVariable("name") String name,
                                          @Valid @PathVariable("password") String password, @Valid @PathVariable("type") int type) {
        if (userRepository.existsByUsername(username)) {
            return new ResponseEntity(new ApiResponse(300, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(email)) {
            return new ResponseEntity(new ApiResponse(400, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (type < 1 || type > 4) {
            return new ResponseEntity(new ApiResponse(500, "type not allowed it must be between 1 & 4"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(name, username,
                email, password, type);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.USER).orElseThrow(null);

        user.setRoles(Collections.singleton(userRole));
        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(200, "User registered successfully"));
    }
}
