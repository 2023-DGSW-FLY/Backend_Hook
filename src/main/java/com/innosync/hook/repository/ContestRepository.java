package com.innosync.hook.repository;

import com.innosync.hook.entity.ContestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<ContestEntity, Long> {
    ContestEntity getContestEntityByTitleContaining(String name);
}
