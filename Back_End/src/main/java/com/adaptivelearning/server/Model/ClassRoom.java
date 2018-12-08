package com.adaptivelearning.server.Model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "classroom")
public class ClassRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classId;

//    @NotNull
//    private int creatorId;

    @ManyToOne
//    @JoinTable(name = "teacher_classrooms",
//            joinColumns = @JoinColumn(name = "classroom_id"),
//            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    @JoinColumn(name = "creator")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User creator;

    @NotNull
    private int classType;

    @NotBlank
    @Size(max = 255)
    private String category;

    @NotBlank
    @Size(max = 255)
    private String passCode;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "student_classrooms",
            joinColumns = {@JoinColumn(name = "classroom_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<User> students;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "parent_classrooms",
            joinColumns = {@JoinColumn(name = "classroom_id")},
            inverseJoinColumns = {@JoinColumn(name = "parent_id")})
    private List<User> parents;

    public ClassRoom() {
    }

    public ClassRoom( @NotNull int classType, @NotBlank @Size(max = 255) String category, @NotBlank @Size(max = 255) String passCode) {
//        this.creator = creator;
        this.classType = classType;
        this.category = category;
        this.passCode = passCode;

    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public List<User> getParents() {
        return parents;
    }

    public void setParents(List<User> parents) {
        this.parents = parents;
    }
}
