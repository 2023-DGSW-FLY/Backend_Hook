package com.innosync.hook.controller;

import com.innosync.hook.dto.HackathonDto;
import com.innosync.hook.service.AccessService;
import com.innosync.hook.dto.AccessDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/access")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AccessController {

    private final AccessService service;

//    // GET: /access/all
//    @GetMapping("/all")
//    public Map<String, List<AccessDto>> getAllAccess() {
//        return service.getAllAccess();
//    }


    @GetMapping("/get")
    public Map<String, List<AccessDto>> getHackathons(@RequestParam(name = "cnt", required = false, defaultValue = "-1") int count) {
        if(count == -1) {
            return service.getAllAccess();
        }
        else {
            // 서비스로부터 모든 데이터 가져오기
            Map<String, List<AccessDto>> allAccess = service.getRecentAccess(count);

            // 원하는 개수만큼 결과 필터링
            Map<String, List<AccessDto>> filteredAccess = allAccess.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
                            .limit(count)
                            .collect(Collectors.toList())));


            return filteredAccess;
        }
    }
    // tag 가져오기 /access/stack?stack=서버개발자
    @GetMapping("/stack")
    public Map<String, Object> getAccessByTag(@RequestParam String job) {
        // 태그를 사용하여 게시물을 가져옵니다.
        return service.getAccessByTag(job);
    }


    // 구직 상태 표시 로직
    // /access/status/complete/
    @PostMapping("/status/complete/{accessId}")
    public ResponseEntity<String> markAccessAsComplete(@PathVariable Long accessId) {
        // accessId를 사용하여 게시글 상태를 변경하는 로직을 호출
        service.changeComplete(accessId);
        return ResponseEntity.ok("게시글 상태가 complete로 변경되었습니다.");
    }
    // /access/status/match/
    @PostMapping("/status/match/{accessId}")
    public ResponseEntity<String> markAccessAsMatching(@PathVariable Long accessId) {
        service.changeMatching(accessId);
        return ResponseEntity.ok("게시글 상태가 matching으로 변경되었습니다.");
    }



    // C POST : /access
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @RequestBody AccessDto dto, Authentication authentication
    ){
        String username = authentication.getName();
        service.register(dto,username);
    }
    // R GET : /{id},
    @GetMapping("/{id}")
    public Map<String, AccessDto> read(
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

    // D DELETE : /{id}

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long id){
        service.remove(id);
    }
}
