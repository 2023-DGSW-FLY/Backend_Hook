package com.innosync.hook.controller;

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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/exercise")
@RestController
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService service;
    private final UserRepository repository;

    // 모든값 가져오기
    @GetMapping("/all")
    public Map<String, List<ExerciseDto>> getAllAccess() {
        return service.getAllAccess();
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
    public void register(
            @RequestBody ExerciseDto dto, Authentication authentication
    ){
        String username = authentication.getName();
        Optional<User> userOptional = repository.findByUserAccount(username);
        User user = userOptional.get(); // User정보 받아서
        Long userId = user.getId(); //정보중 user_id 만 추출하여 userId에 저장
        service.exerciseRegister(dto,username,userId);
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
    @PostMapping("/status/complete/{accessId}")
    public ResponseEntity<String> markAccessAsComplete(@PathVariable Long accessId) {
        // accessId를 사용하여 게시글 상태를 변경하는 로직을 호출
        service.changeComplete(accessId);
        return ResponseEntity.ok("게시글 상태가 complete로 변경되었습니다.");
    }
    // /exercise/status/match/
    @PostMapping("/status/match/{accessId}")
    public ResponseEntity<String> markAccessAsMatching(@PathVariable Long accessId) {
        service.changeMatching(accessId);
        return ResponseEntity.ok("게시글 상태가 matching으로 변경되었습니다.");
    }
}
