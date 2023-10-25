package com.innosync.hook.service;

import com.innosync.hook.dto.SupportDto;

import java.util.List;
import java.util.Map;

public interface SupportService {
    Long applyToHackathon(Long hackathonId, SupportDto supportDto, String userAccount);
    Map<String, List<SupportDto>> getSupportsForHackathon(Long hackathonId);
}
