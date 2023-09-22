package com.innosync.hook.repository;

import com.innosync.hook.entity.ExerciseEntity;
import com.innosync.hook.req.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {
    List<ExerciseEntity>findByExerciseContaining(String ex);
}
