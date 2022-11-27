package com.community.numble.app.user.repository;


import com.community.numble.app.user.domain.User;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByUserId(long userId);
}
