package com.innosync.hook.repository;


import com.innosync.hook.entity.ExerciseEntity;
import com.innosync.hook.entity.FCMEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FCMRepository extends JpaRepository<FCMEntity,Long> {
    List<FCMEntity> findByUserAccount(String userAccount);
}
