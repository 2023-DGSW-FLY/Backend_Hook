package com.innosync.hook.repository;

import com.innosync.hook.entity.AccessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRepository extends JpaRepository<AccessEntity, Long> {
}
