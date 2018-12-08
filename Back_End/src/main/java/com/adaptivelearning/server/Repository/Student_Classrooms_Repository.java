package com.adaptivelearning.server.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adaptivelearning.server.Model.Student_Classrooms;

@Repository
public interface Student_Classrooms_Repository extends JpaRepository <Student_Classrooms, Long>  {
 
}
