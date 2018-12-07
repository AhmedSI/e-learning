package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.ClassRoom;
import com.adaptivelearning.server.Repository.ClassRoomRepository;
import com.adaptivelearning.server.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<ClassRoom> create(@Valid @RequestBody ClassRoom classroom) {
        return new ResponseEntity(classRoomService.save(classroom), HttpStatus.OK);
    }


    @GetMapping("/classrooms")
    Iterable<ClassRoom> read() {
        return classRoomService.findAll();
    }

    @PutMapping("/classrooms")
    ResponseEntity<ClassRoom> update(@RequestBody ClassRoom classRoom) {
        if (classRoomService.findById(classRoom.getClassId()).isPresent())
            return new ResponseEntity(classRoomService.save(classRoom), HttpStatus.OK);
        else
            return new ResponseEntity(classRoom, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/classrooms/{id}")
    void delete(@PathVariable Integer id) {
        classRoomService.deleteById(id);
    }

    @GetMapping("/classrooms/{id}")
    Optional<ClassRoom> findById(@PathVariable Integer id) {
        return classRoomService.findById(id);
    }

    @GetMapping("/classrooms/creator")
    Iterable<ClassRoom> findByQuery(@RequestParam("creator") Integer id) {
        return classRoomService.findByCreatorId(id);
    }
}
