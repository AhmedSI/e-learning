package com.login.GP.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "classroom")
public class ClassRoom {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int classId;

    @NotNull
    private int creatorId;

    private int classType;

    @NotBlank
    @Size (max = 255)
    private String category;

    @NotBlank
    @Size (max = 255)
    private String passCode;


    public ClassRoom(@NotNull int creatorId, int classType, @NotBlank @Size(max = 255) String category, @NotBlank @Size(max = 255) String passCode) {
        this.creatorId = creatorId;
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
}
