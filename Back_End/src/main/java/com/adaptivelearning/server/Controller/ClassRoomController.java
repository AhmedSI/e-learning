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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
public class ClassRoomController {

    @Autowired
    ClassRoomRepository classRoomRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping(Mapping.CLASSROOMS)
    ResponseEntity<ClassRoom> create(@Valid @RequestParam(Param.CATEGORY) String category,
                                     @Valid @RequestParam(Param.CLASSTYPE) Integer classtype,
                                     @Valid @RequestParam(Param.PASSCODE) String passcode) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_email = ((UserPrincipal) principal).getEmail();

        if (userRepository.findByEmail(user_email).getType() == 1) {
            ClassRoom classRoom = new ClassRoom(classtype, category, passcode);
            classRoom.setCreator(userRepository.findByEmail(user_email));
//            classRoom.setPassCode(PasswordEncoder.encode(classRoom.getPassCode()));
            return new ResponseEntity(classRoomRepository.save(classRoom), HttpStatus.OK);
        } else
            return new ResponseEntity(new ApiResponse(405, "type not allowed. Only a teacher could create a classroom"),
                    HttpStatus.FORBIDDEN);
    }

//    @PostMapping("/classrooms")
//    ResponseEntity<ClassRoom> create(@Valid @RequestBody ClassRoom classroom) {
//        return new ResponseEntity(classRoomRepository.save(classroom), HttpStatus.OK);
//    }


    @GetMapping("/classrooms")
    Iterable<ClassRoom> read() {
        return classRoomRepository.findAll();
    }

    @PutMapping("/classrooms")
    ResponseEntity<ClassRoom> update(@RequestBody ClassRoom classRoom) {
        if (classRoomRepository.findById(classRoom.getClassId()).isPresent())
            return new ResponseEntity(classRoomRepository.save(classRoom), HttpStatus.OK);
        else
            return new ResponseEntity(classRoom, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/classrooms/{id}")
    void delete(@PathVariable Integer id) {
        classRoomRepository.deleteById(id);
    }

    @GetMapping("/classrooms/{id}")
    Optional<ClassRoom> findById(@PathVariable Integer id) {
        return classRoomRepository.findById(id);
    }

    @GetMapping("/classrooms/creator")
    Iterable<ClassRoom> findByQuery(@RequestParam("creator") Integer id) {
        return classRoomRepository.findByCreatorId(id);
    }
}
