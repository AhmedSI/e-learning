package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.ClassRoom;
import com.adaptivelearning.server.Repository.ClassRoomRepository;
import com.adaptivelearning.server.Repository.UserRepository;

import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.validation.Valid;


@RestController
public class ClassRoomController {

    @Autowired
    ClassRoomRepository classRoomService;

    @Autowired
    UserRepository userRepository;

    @PostMapping(Mapping.Class_Rooms)
    ResponseEntity<?> create(@Valid @RequestBody ClassRoom classroom){
        classRoomService.save(classroom);
        return ResponseEntity.ok().body(new ApiResponse(200, "Classroom created successfully"));
    }
    @PutMapping(Mapping.Class_Rooms)
    ResponseEntity<?> update(@RequestBody ClassRoom classRoom){
        if(classRoomService.findById(classRoom.getClassId()).isPresent())
        {
            classRoomService.save(classRoom);
            return ResponseEntity.ok().body(new ApiResponse(200, "Classroom updated successfully"));
        }
        else
        {
            return new ResponseEntity(new ApiResponse(500, "Classroom dos not exist"),
                    HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(Mapping.Class_Rooms)
    Iterable<ClassRoom> read(){
        return classRoomService.findAll();
    }

    @DeleteMapping(Mapping.Class_Rooms_Id)
    void delete(@PathVariable Integer id){
        classRoomService.deleteById(id);
    }
    @GetMapping(Mapping.Class_Rooms_Id)
    Optional<ClassRoom> findById(@PathVariable Integer id){
        return classRoomService.findById(id);
    }
    //return all classrooms for creator id
    @GetMapping(Mapping.Class_Rooms_Creator_Id)
    Iterable<ClassRoom> findByQueryCreator(@PathVariable Integer id){
        return classRoomService.findByCreatorId(id);
    }
}
