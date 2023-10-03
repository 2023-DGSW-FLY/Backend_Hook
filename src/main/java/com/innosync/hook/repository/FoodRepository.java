package com.innosync.hook.repository;

import com.innosync.hook.entity.AccessEntity;
import com.innosync.hook.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long>  {
    @Query("SELECT h FROM FoodEntity h ORDER BY h.regDate DESC")
    List<FoodEntity> findTopNByOrderByRegDateDesc(int n);
}

