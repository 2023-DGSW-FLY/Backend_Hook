package com.innosync.hook.repository;

import com.innosync.hook.entity.ContestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestRepository extends JpaRepository<ContestEntity, Long> {
    List<ContestEntity> findAllByTitleContaining(String name);
}
