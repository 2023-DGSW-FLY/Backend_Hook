package com.innosync.hook.controller;

import com.innosync.hook.Service.AccessService;
import com.innosync.hook.dto.AccessDto;
import com.innosync.hook.dto.ContestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/access")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AccessController {

    private final AccessService service;

    // GET: /access/all
    @GetMapping("/all")
    public List<AccessDto> getAllAccess() {
        return service.getAllAccess();
    }


    // C POST : /access
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @RequestBody AccessDto dto
    ){
        log.info("DTO : {}" ,dto);
        service.register(dto);
    }
    // R GET : /{gno},
    @GetMapping("/{id}")
    public AccessDto read(
            @PathVariable("id") Long id
    ){
        log.info("------------------ gno : " + id);
        return service.read(id);
    }

    // U PUT :
    @PutMapping("")
    public ResponseEntity modify(
            @RequestBody AccessDto dto
    ){
        log.info("............. DTO : {}",dto);
        service.modify(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("success");
    }

    // D DELETE : /{gno}

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long id){
        service.remove(id);
    }
}
