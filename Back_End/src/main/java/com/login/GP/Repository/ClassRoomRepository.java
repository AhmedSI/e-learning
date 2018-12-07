package com.login.GP.Repository;


import com.login.GP.Model.ClassRoom;
import org.springframework.data.repository.CrudRepository;


public interface ClassRoomRepository extends CrudRepository<ClassRoom, Integer> {
    Iterable<ClassRoom> findByCreatorId(int creatorId);
}
