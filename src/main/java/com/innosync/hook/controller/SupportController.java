package com.innosync.hook.controller;

import com.innosync.hook.Service.HackathonService;
import com.innosync.hook.Service.SupportService;
import com.innosync.hook.dto.SupportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hackathons/join")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService service;

    @PostMapping("/{hackathonId}/apply")
    public ResponseEntity<Long> applyToHackathon(
            @PathVariable Long hackathonId,
            @RequestBody SupportDto supportDto
    ) {
        Long supportId = service.applyToHackathon(hackathonId, supportDto);
        return ResponseEntity.ok(supportId);
    }

    @GetMapping("/{hackathonId}/supports")
    public ResponseEntity<List<SupportDto>> getSupportsForHackathon(@PathVariable Long hackathonId) {
        List<SupportDto> supports = service.getSupportsForHackathon(hackathonId);
        return ResponseEntity.ok(supports);
    }
}
