package com.innosync.hook.repository;

import com.innosync.hook.entity.ExerciseEntity;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository {
    List<ExerciseEntity> findByStackContaining(String tag);

    Optional<ExerciseEntity> findById(Long accessId);

    void deleteById(Long id);

    void save(ExerciseEntity exerciseEntity);
}
