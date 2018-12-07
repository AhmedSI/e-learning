package com.adaptivelearning.server.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "classroom")
public class ClassRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classId;

    @NotNull
    private int creatorId;

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

    public ClassRoom(@NotNull int creatorId, @NotNull int classType, @NotBlank @Size(max = 255) String category, @NotBlank @Size(max = 255) String passCode, List<User> students, List<User> parents) {
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
