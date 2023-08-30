package com.innosync.hook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innosync.hook.dto.ContestDto;
import com.innosync.hook.Service.ContestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ContestController {

    private final ContestService contestService;

    @GetMapping("/contest")
    public List<ContestDto> getAllContests() {
        return contestService.getAllContests();
    }
}
