package com.adaptivelearning.server.Repository;


import com.adaptivelearning.server.Model.ClassRoom;

import com.adaptivelearning.server.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ClassRoomRepository extends JpaRepository<ClassRoom, Integer> {
    Iterable<ClassRoom> findByCreator(Long creatorId);

//    Iterable<ClassRoom> findByChilds_ChildId(Long childId); //this is not working
}
