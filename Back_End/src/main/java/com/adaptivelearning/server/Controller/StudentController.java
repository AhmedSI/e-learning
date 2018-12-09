package com.adaptivelearning.server.Controller;

import ch.qos.logback.core.encoder.EchoEncoder;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
//@RequestMapping(Mapping.BASE_AUTH)
public class StudentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClassRoomRepository classRoomRepository;

    @SuppressWarnings("unchecked")
    @PostMapping(Mapping.EnrollStudent)
    public ResponseEntity<?> enrollStudent(@Valid @RequestParam(Param.CLASSROOM_ID) Integer classroomId,
                                           @Valid @RequestParam(Param.PASSCODE) String passcode)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_email = ((UserPrincipal) principal).getEmail();
        User user = userRepository.findByEmail(user_email);


        if(classRoomRepository.findById(classroomId).isPresent()){
            ClassRoom classRoom = classRoomRepository.findById(classroomId).get();
            if (user.getType() != 2) {
                return new ResponseEntity(new ApiResponse(400, "InValid User Type For Enrollment"),
                        HttpStatus.BAD_REQUEST);
            }
            if(classRoom.getPassCode().equals(passcode)){
                classRoom.getStudents().add(user);
                user.getEnrolls().add(classRoom);
                classRoomRepository.save(classRoom);

                return new ResponseEntity(new ApiResponse(200, "Student Is Enrolled successfully"),
                        HttpStatus.OK);

            }
            else{
                return new ResponseEntity(new ApiResponse(400, "Passcode is wrong"),
                        HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity(new ApiResponse(404, "Classroom is not found!"),
                    HttpStatus.NOT_FOUND);
        }
//        Optional<Student_Classrooms> Row_Of_This_stID  =  student_classrooms_repository.findById(Classroom_id);

//        if (user.getId() ==  Row_Of_This_stID.get().getStudent_id()) {
//            return new ResponseEntity(new ApiResponse(400, "You Are Already Enrolled In Before"),
//                    HttpStatus.BAD_REQUEST);
//        }
//        Student_Classrooms StudentInClassroom = new Student_Classrooms(Classroom_id , Student_id);
//        student_classrooms_repository.save(StudentInClassroom) ;
//
//        return ResponseEntity.ok().body(new ApiResponse(200, "Student Is Enrolled successfully"));
    }

}