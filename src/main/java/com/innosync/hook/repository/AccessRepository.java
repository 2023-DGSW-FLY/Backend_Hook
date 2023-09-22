package com.innosync.hook.repository;

import com.innosync.hook.entity.AccessEntity;
import com.innosync.hook.entity.HackathonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccessRepository extends JpaRepository<AccessEntity, Long> {
    List<AccessEntity> findByStackContaining(String tag);
    @Query("SELECT h FROM AccessEntity h ORDER BY h.regDate DESC")
    List<AccessEntity> findTopNByOrderByRegDateDesc(int n);
}
