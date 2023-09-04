package com.innosync.hook.controller;

import com.innosync.hook.Service.AccessService;
import com.innosync.hook.Service.HackathonService;
import com.innosync.hook.dto.AccessDto;
import com.innosync.hook.dto.HackathonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/hackathon")
@RestController
@RequiredArgsConstructor
public class HackathonController {

    private final HackathonService service;


    // 모든값 가져오기
    @GetMapping("/all")
    public Map<String, List<HackathonDto>> getAllAccess() {
        return service.getAllAccess();
    }


    // C POST
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @RequestBody HackathonDto dto
    ){
        service.hackathonRegister(dto);
    }
    // R GET : /{id},
    @GetMapping("/{id}")
    public Map<String, HackathonDto> read(@PathVariable("id") Long id){
        return service.hackathonRead(id);
    }

    // U PUT :
    @PutMapping("")
    public ResponseEntity modify(@RequestBody HackathonDto dto){
        service.hackathonModify(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("success");
    }
    // D DELETE : /{id}
    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long id){
        service.hackathonRemove(id);
    }

    // 해커톤 상태 표시 로직
    // /hackathon/status/complete/
    @PostMapping("/status/complete/{accessId}")
    public ResponseEntity<String> markAccessAsComplete(@PathVariable Long accessId) {
        // accessId를 사용하여 게시글 상태를 변경하는 로직을 호출
        service.changeComplete(accessId);
        return ResponseEntity.ok("게시글 상태가 complete로 변경되었습니다.");
    }
    // /hackathon/status/match/
    @PostMapping("/status/match/{accessId}")
    public ResponseEntity<String> markAccessAsMatching(@PathVariable Long accessId) {
        service.changeMatching(accessId);
        return ResponseEntity.ok("게시글 상태가 matching으로 변경되었습니다.");
    }

}
