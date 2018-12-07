package com.login.GP.Controller;

import com.login.GP.Model.ClassRoom;

import com.login.GP.Model.User;
import com.login.GP.Repository.ClassRoomRepository;
import com.login.GP.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import java.util.Optional;


@RestController
public class ClassRoomController {

    @Autowired
    ClassRoomRepository classRoomService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/classrooms")
    ResponseEntity<ClassRoom> create(@Valid @RequestBody ClassRoom classroom){
        if (classroom.getCreator().getType()==1)
            return new ResponseEntity(classRoomService.save(classroom),HttpStatus.OK);
        else
            return new ResponseEntity(classroom,HttpStatus.FORBIDDEN);
    }


    @GetMapping("/classrooms")
    Iterable<ClassRoom> read(){
        return classRoomService.findAll();
    }

    @PutMapping("/classrooms")
    ResponseEntity<ClassRoom> update(@RequestBody ClassRoom classRoom){
        if(classRoomService.findById(classRoom.getClassId()).isPresent())
            return new ResponseEntity(classRoomService.save(classRoom), HttpStatus.OK);
        else
            return new ResponseEntity(classRoom,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/classrooms/{id}")
    void delete(@PathVariable Integer id){
        classRoomService.deleteById(id);
    }

    @GetMapping("/classrooms/{id}")
    Optional<ClassRoom> findById(@PathVariable Integer id){
        return classRoomService.findById(id);
    }

    @GetMapping("/classrooms/creator")
    Iterable<ClassRoom> findByQuery(@RequestParam("creator") Integer id){
        return classRoomService.findByCreatorId(id);
    }
}
