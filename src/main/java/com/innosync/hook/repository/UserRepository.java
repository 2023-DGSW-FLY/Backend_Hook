package com.innosync.hook.repository;

import com.innosync.hook.req.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserAccount(String userAccount);
    Optional<User> findById(Long userId);
}
