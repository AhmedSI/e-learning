package com.adaptivelearning.server.Repository;


import com.adaptivelearning.server.Model.ClassRoom;

import org.springframework.data.repository.CrudRepository;


public interface ClassRoomRepository extends CrudRepository<ClassRoom, Integer> {
    Iterable<ClassRoom> findByCreator(Long creatorId);
}
