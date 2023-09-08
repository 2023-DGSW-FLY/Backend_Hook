package com.innosync.hook.repository;

import com.innosync.hook.entity.FoodEntity;

import java.util.List;
import java.util.Optional;

public interface FoodRepository {
    List<FoodEntity> findByStackContaining(String tag);

    Optional<FoodEntity> findById(Long accessId);

    void deleteById(Long id);

    void save(FoodEntity foodEntity);

    List<FoodEntity> findAll();
}

