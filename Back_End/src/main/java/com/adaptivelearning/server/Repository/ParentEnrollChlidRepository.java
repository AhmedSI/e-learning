package com.adaptivelearning.server.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adaptivelearning.server.Model.Child_joins;

@Repository
public interface ParentEnrollChlidRepository extends JpaRepository<Child_joins, Integer> {

}
