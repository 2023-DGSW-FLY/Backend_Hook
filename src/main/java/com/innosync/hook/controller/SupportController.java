package com.innosync.hook.controller;

import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.req.User;
import com.innosync.hook.service.SupportService;
import com.innosync.hook.dto.SupportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/hackathons/join")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService service;

    @PostMapping("/{hackathonId}/apply")
    public ResponseEntity applyToHackathon(
            @PathVariable Long hackathonId,
            @RequestBody SupportDto supportDto,
            Authentication authentication
    ) {
        return service.applyToHackathon(hackathonId, supportDto, authentication);
    }

    @GetMapping("/{hackathonId}/supports")
    public ResponseEntity<Map<String, List<SupportDto>>> getSupportsForHackathon(@PathVariable Long hackathonId) {
        Map<String, List<SupportDto>> supports = service.getSupportsForHackathon(hackathonId);
        return ResponseEntity.ok(supports);
    }
}
