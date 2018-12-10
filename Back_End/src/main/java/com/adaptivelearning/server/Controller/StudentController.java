package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.ClassRoom;

import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ClassRoomRepository;

import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.UserPrincipal;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;



import javax.validation.Valid;

@RestController
@RequestMapping(Mapping.BASE_AUTH)
public class StudentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClassRoomRepository classRoomRepository;

    @SuppressWarnings("unchecked")
    @PostMapping(Mapping.EnrollStudent)
    public void enrollStudent(@Valid @RequestParam(Param.CLASSROOM_ID) Integer classroomId,
                                           @Valid @RequestParam(Param.PASSCODE) String passcode) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_email = ((UserPrincipal) principal).getEmail();
        User user = userRepository.findByEmail(user_email);

        if (!classRoomRepository.findById(classroomId).isPresent())
            throw new RestClientResponseException("Not found classroom", 404, "NotFound", HttpHeaders.EMPTY, null, null);

        if (user.getType() != 2)
            throw new RestClientResponseException("InValid User Type For Enrollment", 400, "Invalid", HttpHeaders.EMPTY, null, null);

        ClassRoom classRoom = classRoomRepository.findById(classroomId).get();

        if(!classRoom.getPassCode().equals(passcode))
            throw new RestClientResponseException("Passcode is wrong", 400, "Invalid", HttpHeaders.EMPTY, null, null);

        classRoom.getStudents().add(user);
        user.getEnrolls().add(classRoom);//this code line may may may be unnecessary
        classRoomRepository.save(classRoom);
    }

}