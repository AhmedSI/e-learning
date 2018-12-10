package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.ClassRoom;
import com.adaptivelearning.server.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findByToken(String token);

    List<User> findAllByParent(User parent);

    Boolean existsByEmail(String email);
}
