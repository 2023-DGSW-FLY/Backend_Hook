package com.innosync.hook.controller;

import com.innosync.hook.dto.AccessDto;
import com.innosync.hook.repository.ExerciseRepository;
import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.req.User;
import com.innosync.hook.service.ExerciseService;
import com.innosync.hook.dto.ExerciseDto;
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

@RequestMapping("/exercise")
@RestController
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService service;



    // 자신이 작성한 글 모두 받아오기
    @GetMapping("/all")
    public Map<String, Object> getAllMyContest(Authentication authentication){
        return service.getAllMyContest(authentication);
    }

    @GetMapping("/get")
    public Map<String, List<ExerciseDto>> getHackathons(@RequestParam(name = "cnt", required = false, defaultValue = "-1") int count) {
        if(count == -1) {
            return service.getAllAccess();
        }
        else {
            // 서비스로부터 모든 데이터 가져오기
            Map<String, List<ExerciseDto>> allAccess = service.getRecentExercise(count);

            // 원하는 개수만큼 결과 필터링
            Map<String, List<ExerciseDto>> filteredExercise = allAccess.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
                            .limit(count)
                            .collect(Collectors.toList())));


            return filteredExercise;
        }
    }
    // tag 가져오기 /access/ex?ex=배드민턴
    @GetMapping("/ex")
    public Map<String, Object> getAccessByTag(@RequestParam String ex) {
        // 태그를 사용하여 게시물을 가져옵니다.
        return service.getAccessByTag(ex);
    }
    // C POST
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String , String> register(
            @RequestBody ExerciseDto dto, Authentication authentication
    ){
        service.exerciseRegister(dto,authentication);

        Map<String, String> data = new HashMap<>();
        data.put("Success" , "Success");
        return data;
    }


    // R GET : /{id},
    @GetMapping("/{id}")
    public Map<String, ExerciseDto> read(@PathVariable("id") Long id){
        return service.exerciseRead(id);
    }

    // U PUT :
    @PutMapping("")
    public ResponseEntity modify(@RequestBody ExerciseDto dto){
        service.exerciseModify(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("success");
    }
    // D DELETE : /{id}
    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long id){
        service.exerciseRemove(id);
    }

    // 운동 상태 표시 로직
    // /exercise/status/complete/
    @PostMapping("/status/complete/{exerciseId}")
    public Map<String,String> markComplete(@PathVariable Long exerciseId) {
        // accessId를 사용하여 게시글 상태를 변경하는 로직을 호출
        service.changeComplete(exerciseId);
        Map<String, String> data = new HashMap<>();
        data.put("Success" , "Success");

        return data;
    }
    // /access/status/match/
    @PostMapping("/status/match/{exerciseId}")
    public Map<String, String> markMatching(@PathVariable Long exerciseId) {
        service.changeMatching(exerciseId);
        Map<String, String> data = new HashMap<>();
        data.put("Success" , "Success");

        return data;
    }
}
