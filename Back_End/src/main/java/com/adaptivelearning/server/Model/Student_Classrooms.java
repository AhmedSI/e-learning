package com.adaptivelearning.server.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Student_Classrooms {
	@Id
    private Long Classroom_id;
    private Long Student_id ;

public Student_Classrooms () {
	this.Classroom_id = (long) 0 ;
	this.Student_id = (long) 0 ;
}
public Student_Classrooms (Long Classroom_id ,Long Student_id) {
	this.Classroom_id = Classroom_id ;
	this.Student_id = Student_id ;
}
public Long getClassroom_id() {
	return Classroom_id;
}
public void setClassroom_id(Long classroom_id) {
	Classroom_id = classroom_id;
}
public Long getStudent_id() {
	return Student_id;
}
public void setStudent_id(Long student_id) {
	Student_id = student_id;
}

}
