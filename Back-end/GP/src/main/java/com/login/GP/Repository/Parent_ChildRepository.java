package com.login.GP.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.login.GP.Model.Parent_children_relation;

public interface Parent_ChildRepository extends JpaRepository<Parent_children_relation , Integer> {

}
