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
            classRoomRepository.save(classRoom);
            return new ResponseEntity(new ApiResponse(200, " Classroom has been created"),
                    HttpStatus.OK);
        } else {System.out.println(userRepository.findByEmail(user_email).getType());
            return new ResponseEntity(new ApiResponse(405, "type not allowed. Only a teacher could create a classroom"),
                    HttpStatus.FORBIDDEN);}
    }

    /*@GetMapping(Mapping.CLASSROOMS)
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

    }*/


    @PutMapping(Mapping.CLASSROOMS)
    ResponseEntity<?> update(@Valid @RequestParam(Param.CATEGORY) String category,
                             @Valid @RequestParam(Param.CLASSTYPE) Integer classtype,
                             @Valid @RequestParam(Param.PASSCODE) String passcode,
                             @Valid @RequestParam(Param.CLASSROOM_ID) Integer classRoomId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_email = ((UserPrincipal) principal).getEmail();
        User user = userRepository.findByEmail(user_email);

        if (classRoomRepository.findById(classRoomId).isPresent()&&
                classRoomRepository.findById(classRoomId).get().getCreator().getId()==user.getId()){
            ClassRoom classRoom = classRoomRepository.findById(classRoomId).get();
            classRoom.setPassCode(passcode);
            classRoom.setCategory(category);
            classRoom.setClassType(classtype);
            return new ResponseEntity(new ApiResponse(200, " Classroom has been updated"),
                    HttpStatus.OK);
        }
        else if(classRoomRepository.findById(classRoomId).isPresent()&&
                classRoomRepository.findById(classRoomId).get().getCreator().getId()!=user.getId()){
            return new ResponseEntity(new ApiResponse(405, " Not allowed! Only The teacher who has create this classroom could update"),
                    HttpStatus.FORBIDDEN);
        }
        else {
            return new ResponseEntity(new ApiResponse(404, " Classroom is not existed"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(Mapping.CLASSROOMS)
    ResponseEntity<?> delete(@Valid @RequestParam(Param.CLASSROOM_ID) Integer classRoomId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_email = ((UserPrincipal) principal).getEmail();
        User user = userRepository.findByEmail(user_email);
        if (classRoomRepository.findById(classRoomId).isPresent() == false){
            return new ResponseEntity(new ApiResponse(404,"Classroom doesn't exist!"),
                    HttpStatus.NOT_FOUND);
        }
        else if (user.getType()==1 &&
                classRoomRepository.findById(classRoomId).get().getCreator().getId()==user.getId()){
            classRoomRepository.deleteById(classRoomId);
            return new ResponseEntity(new ApiResponse(200,"Classroom has been deleted successfully"),
                    HttpStatus.OK);
        }
        else {
            return new ResponseEntity(new ApiResponse(405,"You Are not allowed to delete this classroom!"),
                    HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(Mapping.CLASSROOMS)
    Optional<ClassRoom> findById(@Valid @RequestParam(Param.CLASSROOM_ID) Integer classroomId) {
        return classRoomRepository.findById(classroomId);

    }
}
