package com.innosync.hook.repository;

import com.innosync.hook.entity.AccessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRepository extends JpaRepository<AccessEntity, Long> {
    List<AccessEntity> findByStackContaining(String tag);
}
