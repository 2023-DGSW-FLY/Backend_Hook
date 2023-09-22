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
import java.util.Optional;

@RestController
@RequestMapping("/hackathons/join")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService service;
    private final UserRepository repository;

    @PostMapping("/{hackathonId}/apply")
    public ResponseEntity<Long> applyToHackathon(
            @PathVariable Long hackathonId,
            @RequestBody SupportDto supportDto,
            Authentication authentication
    ) {
        String username = authentication.getName();
        Optional<User> userOptional = repository.findByUserAccount(username);
        User user = userOptional.get(); // User정보 받아서
        Long userId = user.getId(); //정보중 user_id 만 추출하여 userId에 저장
        Long supportId = service.applyToHackathon(hackathonId, supportDto, userId);
        return ResponseEntity.ok(supportId);
    }

    @GetMapping("/{hackathonId}/supports")
    public ResponseEntity<List<SupportDto>> getSupportsForHackathon(@PathVariable Long hackathonId) {
        List<SupportDto> supports = service.getSupportsForHackathon(hackathonId);
        return ResponseEntity.ok(supports);
    }
}
