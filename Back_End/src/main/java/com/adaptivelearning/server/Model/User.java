package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "client_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int type;

    @NotBlank
    @Size(max = 40)
    private String name;


    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;


    @JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "creator")
    private List<ClassRoom> classrooms;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "students")
    private List<ClassRoom> enrolls;


    @JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "childs")
    private List<ClassRoom> joins;

    @JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},mappedBy = "parent")
    private List<User> children;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent")
    private User parent;

    @Ignore
    private String token;


    public User() {

    }

    public User(String name, String email, String password, int type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<ClassRoom> getEnrolls() {
        return enrolls;
    }

    public void setEnrolls(List<ClassRoom> enrolls) {
        this.enrolls = enrolls;
    }

    public List<ClassRoom> getJoins() {
        return joins;
    }

    public void setJoins(List<ClassRoom> joins) {
        this.joins = joins;
    }


    public List<ClassRoom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<ClassRoom> classrooms) {
        this.classrooms = classrooms;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<User> getChildren() {
        return children;
    }

    public void setChildren(List<User> children) {
        this.children = children;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }
}

