package com.innosync.hook.controller;

import com.innosync.hook.dto.FCMNotificationRequestDto;
import com.innosync.hook.dto.FCMResponseDto;
import com.innosync.hook.req.User;
import com.innosync.hook.service.FCMNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class FCMNotificationApiController {
    private final FCMNotificationService fcmNotificationService;

    @PostMapping()
    public Map<String, String> sendNotificationByToken(@RequestBody FCMNotificationRequestDto requestDto, Authentication authentication){
        String userAccount = authentication.getName(); //유저 아이디임 userAccount
        return fcmNotificationService.sendNotificationService(requestDto,userAccount, "m");
    }

    @GetMapping("/get")
    public Map<String, List<FCMResponseDto>> showDataFCM(Authentication authentication){
        String userAccount = authentication.getName();
        return fcmNotificationService.showDataFCM(userAccount);
    }
}
