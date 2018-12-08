package com.adaptivelearning.server.Controller;

	import com.adaptivelearning.server.Model.Student_Classrooms;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.Student_Classrooms_Repository;
import com.adaptivelearning.server.Repository.UserRepository;
	import com.adaptivelearning.server.constants.Mapping;
	import com.adaptivelearning.server.constants.Param;
	import com.adaptivelearning.server.payload.ApiResponse;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.validation.Valid;

	@RestController
	@RequestMapping(Mapping.BASE_AUTH)
public class StudentController {
		
		    @Autowired
		    UserRepository userRepository;
		    
		    @Autowired
		    Student_Classrooms_Repository student_classrooms_repository;

		    @SuppressWarnings("unchecked")
		    @PostMapping(Mapping.EnrollStudent)
		    public ResponseEntity<?> enrollStudent(@Valid @RequestParam(Param.CLASSROOM_ID) Long Classroom_id,
		    		                               @Valid @RequestParam(Param.STUDENT_ID) Long Student_id)
		    		{
		    	Optional<User>  enrollStudent =  userRepository.findById(Student_id);
		    	Optional<Student_Classrooms> Row_Of_This_stID  =  student_classrooms_repository.findById(Classroom_id);
		    	
		        if (enrollStudent.get().getType() != 2) {
		            return new ResponseEntity(new ApiResponse(400, "InValid User Type For Enrollment"),
		                    HttpStatus.BAD_REQUEST);
		        }
		        if (Student_id ==  Row_Of_This_stID.get().getStudent_id()) {
		            return new ResponseEntity(new ApiResponse(400, "You Are Already Enrolled In Before"),
		                    HttpStatus.BAD_REQUEST);
		        }
		        Student_Classrooms StudentInClassroom = new Student_Classrooms(Classroom_id , Student_id);
		        student_classrooms_repository.save(StudentInClassroom) ;
		        
		     return ResponseEntity.ok().body(new ApiResponse(200, "Student Is Enrolled successfully"));    
}
		   
	}
