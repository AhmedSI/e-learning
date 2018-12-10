package com.adaptivelearning.server.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adaptivelearning.server.Model.Parent_Children_Relation;

@Repository
public interface Parent_Children_Relation_Repository  extends JpaRepository <Parent_Children_Relation, Long> {
	//User findByEmail(String email);

    //Boolean existsByEmail(String email);
}
