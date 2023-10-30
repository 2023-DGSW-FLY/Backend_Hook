package com.innosync.hook.service;

import com.innosync.hook.dto.SupportDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SupportService {
    ResponseEntity applyToHackathon(Long hackathonId, SupportDto supportDto, String userAccount);
    Map<String, List<SupportDto>> getSupportsForHackathon(Long hackathonId);
}
