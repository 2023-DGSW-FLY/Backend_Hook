package com.innosync.hook.repository;

import com.innosync.hook.entity.HackathonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HackathonRepository extends JpaRepository<HackathonEntity, Long> {
    List<HackathonEntity>findByStackContaining(String tag);
}
