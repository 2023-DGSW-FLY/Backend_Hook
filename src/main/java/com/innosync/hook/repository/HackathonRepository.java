package com.innosync.hook.repository;

import com.innosync.hook.entity.HackathonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackathonRepository extends JpaRepository<HackathonEntity, Long> {
}
