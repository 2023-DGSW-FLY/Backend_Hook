package com.innosync.hook.controller;

import com.innosync.hook.dto.FCMNotificationRequestDto;
import com.innosync.hook.req.User;
import com.innosync.hook.service.FCMNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class FCMNotificationApiController {
    private final FCMNotificationService fcmNotificationService;

    @PostMapping()
    public Map<String, String> sendNotificationByToken(@RequestBody FCMNotificationRequestDto requestDto, Authentication authentication){
        String userName = authentication.getName();
        return fcmNotificationService.sendNotificationService(requestDto,userName);
    }
}
