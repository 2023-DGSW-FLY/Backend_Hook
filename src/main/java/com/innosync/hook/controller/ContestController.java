package com.innosync.hook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innosync.hook.dto.ContestDto;
import com.innosync.hook.service.ContestService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ContestController {

    private final ContestService contestService;

    @GetMapping("/contest")
    public Map<String, List<ContestDto>> getAllContests() {
        return contestService.getAllContests();
    }
}
