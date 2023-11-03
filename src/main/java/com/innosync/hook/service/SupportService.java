package com.innosync.hook.service;

import com.innosync.hook.dto.SupportDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface SupportService {
    ResponseEntity applyToHackathon(Long hackathonId, SupportDto supportDto, Authentication authentication);
    Map<String, List<SupportDto>> getSupportsForHackathon(Long hackathonId);
}
