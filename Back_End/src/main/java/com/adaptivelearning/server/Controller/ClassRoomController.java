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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;

import javax.validation.Valid;


@RestController
public class ClassRoomController {

    @Autowired
    ClassRoomRepository classRoomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(Mapping.CLASSROOMS)
    ClassRoom create(@Valid @RequestParam(Param.CATEGORY) String category,
                     @Valid @RequestParam(Param.CLASSTYPE) Integer classtype,
                     @Valid @RequestParam(Param.PASSCODE) String passcode) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_email = ((UserPrincipal) principal).getEmail();

        if (userRepository.findByEmail(user_email).getType() != 1)
            throw new RestClientResponseException("User is not a teacher", 405, "NotAllowed", HttpHeaders.EMPTY, null, null);

        ClassRoom classRoom = new ClassRoom(classtype, category, passcode);
        classRoom.setCreator(userRepository.findByEmail(user_email));
        classRoom.setPassCode(passwordEncoder.encode(classRoom.getPassCode()));
        classRoomRepository.save(classRoom);

        return classRoom;
    }

    @GetMapping(Mapping.CLASSROOMS)
    Iterable<ClassRoom> read() {
        Object principal =  (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String user_email = ((UserPrincipal) principal).getEmail();
        User user = userRepository.findByEmail(user_email);
        if(user.getType()==1){
            return user.getClassrooms();
        }
        else if(user.getType()==2) {
            return user.getEnrolls();
        }
        //// i'm sorry i'm tired of thinking so please someone do this retrieve you have to retrieve classrooms of children of this guy, enjoy!
//        else if (user.getType()==3){
////            return new ResponseEntity.(new ApiResponse(400, "user cannot enroll now"),
////                    HttpStatus.BAD_REQUEST);
//            return classRoomRepository.
//        }
        else if (user.getType()==4){
            return user.getJoins();
        }
        else {
            return new ArrayList<ClassRoom>();
        }

    }


    @PutMapping(Mapping.CLASSROOMS)
    ClassRoom update(@Valid @RequestParam(Param.CATEGORY) String category,
                             @Valid @RequestParam(Param.CLASSTYPE) Integer classtype,
                             @Valid @RequestParam(Param.PASSCODE) String passcode,
                             @Valid @RequestParam(Param.CLASSROOM_ID) Integer classRoomId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_email = ((UserPrincipal) principal).getEmail();
        User user = userRepository.findByEmail(user_email);

        if (!classRoomRepository.findById(classRoomId).isPresent())
            throw new RestClientResponseException("Not found classroom", 404, "NotFound", HttpHeaders.EMPTY, null, null);

        if (!classRoomRepository.findById(classRoomId).get().getCreator().getId().equals(user.getId()))
            throw new RestClientResponseException("Not Allowed you are not a teacher", 405, "NotAllowed", HttpHeaders.EMPTY, null, null);

        ClassRoom classRoom = classRoomRepository.findById(classRoomId).get();
        classRoom.setPassCode(passcode);
        classRoom.setCategory(category);
        classRoom.setClassType(classtype);
        return classRoom;
    }

    @DeleteMapping(Mapping.CLASSROOMS)
    void delete(@Valid @RequestParam(Param.CLASSROOM_ID) Integer classRoomId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_email = ((UserPrincipal) principal).getEmail();
        User user = userRepository.findByEmail(user_email);

        if (!classRoomRepository.findById(classRoomId).isPresent())
            throw new RestClientResponseException("Not found classroom", 404, "NotFound", HttpHeaders.EMPTY, null, null);

        if(user.getType()!=1 ||
                !classRoomRepository.findById(classRoomId).get().getCreator().getId().equals(user.getId()))
            throw new RestClientResponseException("Only creator of this classroom is allowed", 405, "NotAllowed", HttpHeaders.EMPTY, null, null);

        classRoomRepository.deleteById(classRoomId);
    }

    @GetMapping(Mapping.CLASSROOM)
    ClassRoom findById(@Valid @RequestParam(Param.CLASSROOM_ID) Integer classRoomId) {
        if(!classRoomRepository.findById(classRoomId).isPresent())
            throw new RestClientResponseException("Not found classroom", 404, "NotFound", HttpHeaders.EMPTY, null, null);


        return classRoomRepository.findById(classRoomId).get();
    }
}
