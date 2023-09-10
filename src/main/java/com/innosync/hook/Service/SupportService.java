package com.innosync.hook.Service;

import com.innosync.hook.dto.SupportDto;

import java.util.List;

public interface SupportService {
    Long applyToHackathon(Long hackathonId, SupportDto supportDto);
    List<SupportDto> getSupportsForHackathon(Long hackathonId);
}
