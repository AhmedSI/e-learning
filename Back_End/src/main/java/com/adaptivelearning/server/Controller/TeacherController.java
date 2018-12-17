package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.ClassRoom;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ClassRoomRepository;
import com.adaptivelearning.server.Repository.UserRepository;

import com.adaptivelearning.server.Security.UserPrincipal;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;
import com.adaptivelearning.server.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;

@RestController
public class TeacherController {
	
	@Autowired
    ClassRoomRepository classRoomRepository;

    @Autowired
    UserRepository userRepository;


	@GetMapping(Mapping.TeacherGetClassrooms)
    Iterable<ClassRoom> findByQuery() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = ((UserPrincipal) principal).getId();
        String user_email = ((UserPrincipal) principal).getEmail();
        if (userRepository.findByEmail(user_email).getType() == 1) {
        	System.out.println(userRepository.findByEmail(user_email).getType());
        	
            return classRoomRepository.findByCreator(userRepository.findByEmail(user_email));
        }
        else
            {
            return null;//will be fixed
            }

    }
}
