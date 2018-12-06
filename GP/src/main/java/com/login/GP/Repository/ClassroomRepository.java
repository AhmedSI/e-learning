package com.login.GP.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.login.GP.Model.Classroom;
@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer>{

}
