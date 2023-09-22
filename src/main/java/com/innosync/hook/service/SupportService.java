package com.innosync.hook.service;

import com.innosync.hook.dto.SupportDto;

import java.util.List;

public interface SupportService {
    Long applyToHackathon(Long hackathonId, SupportDto supportDto, Long username);
    List<SupportDto> getSupportsForHackathon(Long hackathonId);
}
