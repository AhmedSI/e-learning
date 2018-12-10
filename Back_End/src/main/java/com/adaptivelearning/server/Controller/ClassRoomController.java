package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.ClassRoom;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.ClassRoomRepository;
import com.adaptivelearning.server.Repository.UserRepository;

import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.Security.UserPrincipal;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

import java.util.*;

import javax.validation.Valid;


@RestController
public class ClassRoomController {

    @Autowired
    ClassRoomRepository classRoomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenChecker;

    @PostMapping(Mapping.CLASSROOMS)
    ClassRoom create(@RequestParam(Param.ACCESSTOKEN) String token,
                     @Valid @RequestParam(Param.CATEGORY) String category,
                     @Valid @RequestParam(Param.CLASSTYPE) Integer classtype,
                     @Valid @RequestParam(Param.PASSCODE) String passcode) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String user_email = ((UserPrincipal) principal).getEmail();

        //// the new way
        if(!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        //        //Long user_id = jwtTokenChecker.getUserIdFromToken(token);
        User user = userRepository.findByToken(token).get();

        if (user.getType() != 1)
            throw new RestClientResponseException("User is not a teacher", 405, "NotAllowed", HttpHeaders.EMPTY, null, null);


        ClassRoom classRoom = new ClassRoom(classtype, category, passcode);
        classRoom.setCreator(user);
        classRoom.setPassCode(passwordEncoder.encode(classRoom.getPassCode()));
        classRoomRepository.save(classRoom);

        return classRoom;
    }

    @GetMapping(Mapping.CLASSROOMS)
    Iterable<ClassRoom> retrieveClassrooms(@RequestParam(Param.ACCESSTOKEN) String token) {
//        Object principal =  (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        String user_email = ((UserPrincipal) principal).getEmail();

        //// the new way
        if(!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        //        //Long user_id = jwtTokenChecker.getUserIdFromToken(token);
        User user = userRepository.findByToken(token).get();

        if(user.getType()==1){
            return user.getClassrooms();
        }
        else if(user.getType()==2) {
            return user.getEnrolls();
        }
        else if (user.getType()==4){
            return user.getJoins();
        }
        else {
            return new ArrayList<ClassRoom>();
        }

    }

    //don't delete this it's need to be implemented
    //// i'm sorry i'm tired of thinking so please someone check this retrieve you have to retrieve classrooms of children of this guy, enjoy!
//    @GetMapping(Mapping.CHILDRENCLASSROOMS)
//    Map<User,List<ClassRoom>> retrieveChildrenClassrooms(@RequestParam(Param.ACCESSTOKEN) String token) {
//        //// the new way
//        if (!userRepository.findByToken(token).isPresent())
//            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);
//
//        if (!jwtTokenChecker.validateToken(token))
//            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);
//
//        //        //Long user_id = jwtTokenChecker.getUserIdFromToken(token);
//        User user = userRepository.findByToken(token).get();
//
//        if(user.getType()!=3)
//            throw new RestClientResponseException("User is not a teacher", 405, "NotAllowed", HttpHeaders.EMPTY, null, null);
//
//        Map<User,List<ClassRoom>> childrensClassrooms = new HashMap<>();
//        List<User> children = userRepository.findAllByParent(user);
//        for (int i =0;i<children.size();i++){
//            Iterable<ClassRoom> classRoomsIterable = classRoomRepository.findByChilds_ChildId(children.get(i).getId());
//            List<ClassRoom> classRoomsList = new ArrayList<ClassRoom>();
//            for (ClassRoom classroom:
//                    classRoomsIterable){
//                classRoomsList.add(classroom);
//            }
//            childrensClassrooms.put(children.get(i), classRoomsList);
//        }
//        return childrensClassrooms;
//
//
//    }


    @PutMapping(Mapping.CLASSROOMS)
    ClassRoom update(@RequestParam(Param.ACCESSTOKEN) String token,
                     @Valid @RequestParam(Param.CATEGORY) String category,
                     @Valid @RequestParam(Param.CLASSTYPE) Integer classtype,
                     @Valid @RequestParam(Param.PASSCODE) String passcode,
                     @Valid @RequestParam(Param.CLASSROOM_ID) Integer classRoomId) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String user_email = ((UserPrincipal) principal).getEmail();
//        User user = userRepository.findByEmail(user_email);
        if (!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        //        //Long user_id = jwtTokenChecker.getUserIdFromToken(token);
        User user = userRepository.findByToken(token).get();

        if (!classRoomRepository.findById(classRoomId).isPresent())
            throw new RestClientResponseException("Not found classroom", 404, "NotFound", HttpHeaders.EMPTY, null, null);

        if (!classRoomRepository.findById(classRoomId).get().getCreator().getId().equals(user.getId()))
            throw new RestClientResponseException("Not Allowed you are not a teacher or this is not your classroom to update", 405, "NotAllowed", HttpHeaders.EMPTY, null, null);

        ClassRoom classRoom = classRoomRepository.findById(classRoomId).get();
        classRoom.setPassCode(passcode);
        classRoom.setCategory(category);
        classRoom.setClassType(classtype);
        return classRoom;
    }

    @DeleteMapping(Mapping.CLASSROOMS)
    void delete(@RequestParam(Param.ACCESSTOKEN) String token,
                @Valid @RequestParam(Param.CLASSROOM_ID) Integer classRoomId) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String user_email = ((UserPrincipal) principal).getEmail();
//        User user = userRepository.findByEmail(user_email);
        if (!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        //        //Long user_id = jwtTokenChecker.getUserIdFromToken(token);
        User user = userRepository.findByToken(token).get();

        if (!classRoomRepository.findById(classRoomId).isPresent())
            throw new RestClientResponseException("Not found classroom", 404, "NotFound", HttpHeaders.EMPTY, null, null);

        if(user.getType()!=1 ||
                !classRoomRepository.findById(classRoomId).get().getCreator().getId().equals(user.getId()))
            throw new RestClientResponseException("Only creator of this classroom is allowed", 405, "NotAllowed", HttpHeaders.EMPTY, null, null);

        classRoomRepository.deleteById(classRoomId);
    }

    @GetMapping(Mapping.CLASSROOM)
    ClassRoom findById(@RequestParam(Param.ACCESSTOKEN) String token,
                       @Valid @RequestParam(Param.CLASSROOM_ID) Integer classRoomId) {

        if (!userRepository.findByToken(token).isPresent())
            throw new RestClientResponseException("Invalid token", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        if (!jwtTokenChecker.validateToken(token))
            throw new RestClientResponseException("Session expired", 400, "BadRequest", HttpHeaders.EMPTY, null, null);

        //        //Long user_id = jwtTokenChecker.getUserIdFromToken(token);
        User user = userRepository.findByToken(token).get();

        if(!classRoomRepository.findById(classRoomId).isPresent())
            throw new RestClientResponseException("Not found classroom", 404, "NotFound", HttpHeaders.EMPTY, null, null);


        return classRoomRepository.findById(classRoomId).get();
    }
}
