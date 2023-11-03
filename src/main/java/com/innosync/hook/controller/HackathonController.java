package com.innosync.hook.controller;

import com.innosync.hook.dto.AccessDto;
import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.req.User;
import com.innosync.hook.service.HackathonService;
import com.innosync.hook.dto.HackathonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/hackathon")
@RestController
@RequiredArgsConstructor
public class HackathonController {

    private final HackathonService service;

    // 자신이 작성한 모든 대회글 불러오기
    @GetMapping("/all")
    public Map<String, List<HackathonDto>> getAllMyContest(Authentication authentication){
        String username = authentication.getName();
        return service.getAllMyContest(username);
    }


//    모든값 가져오기
//    @GetMapping("/all")
//    public Map<String, List<HackathonDto>> getAllAccess() {
//        return service.getAllAccess();
//    }

    // tag 가져오기 /access/stack?stack=서버개발자
    @GetMapping("/stack")
    public Map<String, Object> getAccessByTag(@RequestParam String job) {
        // 태그를 사용하여 게시물을 가져옵니다.
        return service.getAccessByTag(job);
    }

    @GetMapping("/get")
    public Map<String, List<HackathonDto>> getHackathons(@RequestParam(name = "cnt", required = false, defaultValue = "-1") int count) {
        if(count == -1) {
            return service.getAllAccess();
        }
        else {
            // 서비스로부터 모든 데이터 가져오기
            Map<String, List<HackathonDto>> allHackathons = service.getRecentHackathons(count);

            // 원하는 개수만큼 결과 필터링
            Map<String, List<HackathonDto>> filteredHackathons = allHackathons.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
                            .limit(count)
                            .collect(Collectors.toList())));


            return filteredHackathons;
        }
    }


    // C POST
    @PostMapping("")
    public Map<String, String> register(
            @RequestBody HackathonDto dto , Authentication authentication
    ){

        service.hackathonRegister(dto, authentication);
        Map<String, String> data = new HashMap<>();
        data.put("Success" , "Success");
        return data;
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
    // 구인 상태 표시 로직
    // /hackathon/status/complete/
    @PostMapping("/status/complete/{hackathonId}")
    public Map<String,String> markAccessAsComplete(@PathVariable Long hackathonId) {
        // accessId를 사용하여 게시글 상태를 변경하는 로직을 호출
        service.changeComplete(hackathonId);
        Map<String, String> data = new HashMap<>();
        data.put("Success" , "Success");

        return data;
    }
    // /hackathon/status/match/
    @PostMapping("/status/match/{hackathonId}")
    public Map<String, String> markAccessAsMatching(@PathVariable Long hackathonId) {
        service.changeMatching(hackathonId);
        Map<String, String> data = new HashMap<>();
        data.put("Success" , "Success");

        return data;
    }
}
