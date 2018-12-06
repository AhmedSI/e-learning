package com.login.GP.Controller;

import com.login.GP.Model.ClassRoom;

import com.login.GP.Repository.ClassRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.Optional;


@RestController
public class ClassRoomController {

    @Autowired
    ClassRoomRepository classRoomService;

    @PostMapping("/classrooms")
    ClassRoom create(@Valid @RequestBody ClassRoom classroom){
        return classRoomService.save(classroom);
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

}
