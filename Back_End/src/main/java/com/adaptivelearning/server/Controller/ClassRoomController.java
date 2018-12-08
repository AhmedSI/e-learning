package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.ClassRoom;
import com.adaptivelearning.server.Repository.ClassRoomRepository;
import com.adaptivelearning.server.Repository.UserRepository;

import com.adaptivelearning.server.Security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.validation.Valid;


@RestController
public class ClassRoomController {

    @Autowired
    ClassRoomRepository classRoomService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/classrooms")
    ResponseEntity<ClassRoom> create(@Valid @RequestBody ClassRoom classroom){
        return new ResponseEntity(classRoomService.save(classroom),HttpStatus.OK);
    }
    @PutMapping("/classrooms")
    ResponseEntity<ClassRoom> update(@RequestBody ClassRoom classRoom){
        if(classRoomService.findById(classRoom.getClassId()).isPresent())
            return new ResponseEntity(classRoomService.save(classRoom), HttpStatus.OK);
        else
            return new ResponseEntity(classRoom,HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/classrooms")
    Iterable<ClassRoom> read(){
        return classRoomService.findAll();
    }

    @DeleteMapping("/classrooms/{id}")
    void delete(@PathVariable Integer id){
        classRoomService.deleteById(id);
    }
    @GetMapping("/classrooms/{id}")
    Optional<ClassRoom> findById(@PathVariable Integer id){
        return classRoomService.findById(id);
    }
    @GetMapping("/classrooms/retrive/{id}")
    Iterable<ClassRoom> findByQuery(@PathVariable Integer id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_email = ((UserPrincipal) principal).getEmail();
        if (userRepository.findByEmail(user_email).getType() == 1) {
            return classRoomService.findByCreatorId(id);
        }
        else
            {
            return null;//will be fixed
            }

    }
}
