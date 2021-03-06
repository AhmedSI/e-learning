package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Role;
import com.adaptivelearning.server.Model.RoleName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
