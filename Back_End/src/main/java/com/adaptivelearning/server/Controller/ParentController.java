package com.adaptivelearning.server.Controller;

	import com.adaptivelearning.server.Model.Child_joins;
import com.adaptivelearning.server.Model.Parent_Children_Relation;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ParentEnrollChlidRepository;
import com.adaptivelearning.server.Repository.Parent_Children_Relation_Repository;
import com.adaptivelearning.server.Repository.RoleRepository;
	import com.adaptivelearning.server.Repository.UserRepository;
	import com.adaptivelearning.server.Security.JwtTokenProvider;
	import com.adaptivelearning.server.constants.Mapping;
	import com.adaptivelearning.server.constants.Param;
	import com.adaptivelearning.server.payload.ApiResponse;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.validation.Valid;

	@RestController
	@RequestMapping(Mapping.BASE_AUTH)
public class ParentController {

	    @Autowired
	    AuthenticationManager authenticationManager;

	    @Autowired
	    UserRepository userRepository;

	    @Autowired
	    RoleRepository roleRepository;

	    @Autowired
	    PasswordEncoder passwordEncoder;

	    @Autowired
	    JwtTokenProvider tokenProvider;
	    
	    @Autowired
	    Parent_Children_Relation_Repository parent_children_relation_repository;

	    @SuppressWarnings("unchecked")
	    @PostMapping(Mapping.AddChild)
	    public ResponseEntity<?> addChild(@Valid @RequestParam(Param.PARENT_ID) Long Parent_id ,
	    		                              @Valid @RequestParam(Param.EMAIL) String email,
	                                          @Valid @RequestParam(Param.NAME) String name,
	                                          @Valid @RequestParam(Param.PASSWORD) String password,
	                                          @Valid @RequestParam(Param.TYPE) int type) {
	        if (userRepository.existsByEmail(email)) {
	            return new ResponseEntity(new ApiResponse(400, "Email Address already in use!"),
	                    HttpStatus.BAD_REQUEST);
	        }
	        if (type != 4) {
	            return new ResponseEntity(new ApiResponse(500, "InValid User Type"),
	                    HttpStatus.BAD_REQUEST);
	        }

	        // Creating Child account
	        User Added_Child = new User(name, email, password, type);
	        
	        Added_Child.setPassword(passwordEncoder.encode(Added_Child.getPassword()));

	        userRepository.save(Added_Child);
	        
	        Added_Child = userRepository.findByEmail(email);// search for added child to get id
	        
	        Parent_Children_Relation Parent_Child = new Parent_Children_Relation(Added_Child.getId() , Parent_id);
	        
	        parent_children_relation_repository.save(Parent_Child);

	        return ResponseEntity.ok().body(new ApiResponse(200, "Child is Added successfully"));
	    }
	    
	    @Autowired
	    ParentEnrollChlidRepository enrollchild;

	    @PostMapping(Mapping.PARENTENROLL)
	    ResponseEntity<?> enroll(@Valid @RequestParam(Param.PARENT_ID) int parentId,@Valid @RequestParam(Param.ChildId) int childId,@Valid @RequestParam(Param.CLASSROOM_ID) int classId) {
	    	Optional<User>  enrollChild =  userRepository.findById((long) childId);
	    	
	    	if(enrollChild.get().getType()!= 4)
	    	{
	    		  return new ResponseEntity(new ApiResponse(400, "InValid User Type For Enrollment"),
		                    HttpStatus.BAD_REQUEST);
		      
	    	}
	    	else {
	    	Child_joins child=new Child_joins(parentId, childId, classId);
	        enrollchild.save(child);
	        return ResponseEntity.ok().body(new ApiResponse(200, "child enrolled to the classroom successfully"));
	    	}
	    }
	    	
	    
	}
