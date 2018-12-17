package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.ClassRoom;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ClassRoomRepository;
import com.adaptivelearning.server.Repository.RoleRepository;
import com.adaptivelearning.server.Repository.UserRepository;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.Security.UserPrincipal;
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
//@RequestMapping(Mapping.BASE_AUTH)
public class ParentController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClassRoomRepository classRoomRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;


    @SuppressWarnings("unchecked")
    @PostMapping(Mapping.AddChild)
    public ResponseEntity<?> addChild(@Valid @RequestParam(Param.EMAIL) String email,
                                      @Valid @RequestParam(Param.NAME) String name,
                                      @Valid @RequestParam(Param.PASSWORD) String password) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_email = ((UserPrincipal) principal).getEmail();
        User parent = userRepository.findByEmail(user_email);

        if (userRepository.existsByEmail(email)) {
            return new ResponseEntity(new ApiResponse(400, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        //// not necessary
//        if (type != 4) {
//            return new ResponseEntity(new ApiResponse(500, "InValid User Type"),
//                    HttpStatus.BAD_REQUEST);
//        }

        // Creating Child account
        User child = new User(name, email, password,4);

        child.setPassword(passwordEncoder.encode(child.getPassword()));
        child.setParent(parent);

        parent.getChildren().add(child);
        userRepository.save(child);

//        Added_Child = userRepository.findByEmail(email);// search for added child to get id
//
//        Parent_Children_Relation Parent_Child = new Parent_Children_Relation(Added_Child.getId() , Parent_id);
//
//        parent_children_relation_repository.save(Parent_Child);

        return ResponseEntity.ok().body(new ApiResponse(200, "Child is Added successfully"));
    }


    @PostMapping(Mapping.PARENTENROLL)
    ResponseEntity<?> enroll(@Valid @RequestParam(Param.USER_ID) int childId,
                             @Valid @RequestParam(Param.CLASSROOM_ID) int classId) {
        Optional<User>  enrollChild =  userRepository.findById((long) childId);
        Optional<ClassRoom> classRoomSearch = classRoomRepository.findById(classId);
        if (enrollChild.isPresent()&&classRoomSearch.isPresent()){
            ClassRoom classRoom = classRoomSearch.get();
            if(enrollChild.get().getType()!= 4)
            {
                return new ResponseEntity(new ApiResponse(400, "InValid User Type For Enrollment"),
                        HttpStatus.BAD_REQUEST);

            }
            else {
//                Child_joins child=new Child_joins(parentId, childId, classId);
//                enrollchild.save(child);
                classRoom.getChilds().add(enrollChild.get());
                classRoomRepository.save(classRoom);
                return ResponseEntity.ok().body(new ApiResponse(200, "child enrolled to the classroom successfully"));
            }
        }
        else {
            return new ResponseEntity(new ApiResponse(404, "Not found Child or classroom"),
                    HttpStatus.NOT_FOUND);
        }

    }


}

