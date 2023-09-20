package com.innosync.hook.repository;

import com.innosync.hook.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long>  {
}

