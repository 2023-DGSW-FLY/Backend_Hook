package com.innosync.hook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innosync.hook.dto.ContestDto;
import com.innosync.hook.Service.ContestService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContestController {

    private final ContestService contestService;

    @Autowired
    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }

    @GetMapping("/contest")
    public List<ContestDto> getAllContests() {
        return contestService.getAllContests();
    }
}
