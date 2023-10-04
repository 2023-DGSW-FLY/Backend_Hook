package com.innosync.hook.repository;

import com.innosync.hook.entity.HackathonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HackathonRepository extends JpaRepository<HackathonEntity, Long> {
    List<HackathonEntity>findByStackContaining(String tag);
    List<HackathonEntity>findByWriterContaining(String tag);
    @Query("SELECT h FROM HackathonEntity h ORDER BY h.regDate DESC")
    List<HackathonEntity> findTopNByOrderByRegDateDesc(int n);

}
