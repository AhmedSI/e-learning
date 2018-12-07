package com.login.GP.Model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classroom")
public class ClassRoom {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int classId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinTable(name = "teacher_classrooms",
//            joinColumns = @JoinColumn(name = "classroom_id"),
//            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private User creator;
    @NotNull
    private int creatorId;

    @NotNull
    private int classType;

    @NotBlank
    @Size (max = 255)
    private String category;

    @NotBlank
    @Size (max = 255)
    private String passCode;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "student_classrooms",
            joinColumns = {@JoinColumn(name = "classroom_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private Set<User> students = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "parent_classrooms",
            joinColumns = {@JoinColumn(name = "classroom_id")},
            inverseJoinColumns = {@JoinColumn(name = "parent_id")})
    private Set<User> parents = new HashSet<>();

    public ClassRoom() {
    }

    public ClassRoom(@NotNull int creatorId, @NotNull int classType, @NotBlank @Size(max = 255) String category, @NotBlank @Size(max = 255) String passCode, Set<User> students, Set<User> parents) {
        this.creatorId = creatorId;
        this.classType = classType;
        this.category = category;
        this.passCode = passCode;
        this.students = students;
        this.parents = parents;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
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

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public Set<User> getParents() {
        return parents;
    }

    public void setParents(Set<User> parents) {
        this.parents = parents;
    }
}
