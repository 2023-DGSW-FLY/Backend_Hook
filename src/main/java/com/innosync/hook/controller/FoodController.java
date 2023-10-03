package com.innosync.hook.controller;

import com.innosync.hook.dto.ExerciseDto;
import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.req.User;
import com.innosync.hook.service.FoodService;
import com.innosync.hook.dto.FoodDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/food")
@RestController
@RequiredArgsConstructor
public class FoodController {
    private final FoodService service;
    private final UserRepository repository;

//    // 모든값 가져오기
//    @GetMapping("/all")
//    public Map<String, List<FoodDto>> getAllAccess() {
//        return service.getAllAccess();
//    }
    // 게시글 파라미터로 받아오기 if get 이후 cnt 값이 없다면 모든값 리턴 /get?cnt=1 or /get
    @GetMapping("/get")
    public Map<String, List<FoodDto>> getHackathons(@RequestParam(name = "cnt", required = false, defaultValue = "-1") int count) {
        if(count == -1) {
            return service.getAllAccess();
        }
        else {
            // 서비스로부터 모든 데이터 가져오기
            Map<String, List<FoodDto>> allAccess = service.getRecentFood(count);

            // 원하는 개수만큼 결과 필터링
            Map<String, List<FoodDto>> filteredFood = allAccess.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
                            .limit(count)
                            .collect(Collectors.toList())));


            return filteredFood;
        }
    }

    // C POST
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @RequestBody FoodDto dto , Authentication authentication

    ){
        String username = authentication.getName();
        Optional<User> userOptional = repository.findByUserAccount(username);
        User user = userOptional.get(); // User정보 받아서
        Long userId = user.getId(); //정보중 user_id 만 추출하여 userId에 저장
        service.foodRegister(dto,username,userId);
    }
    // R GET : /{id},
    @GetMapping("/{id}")
    public Map<String, FoodDto> read(@PathVariable("id") Long id){
        return service.foodRead(id);
    }

    // U PUT :
    @PutMapping("")
    public ResponseEntity modify(@RequestBody FoodDto dto){
        service.foodModify(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("success");
    }
    // D DELETE : /{id}
    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long id){
        service.foodRemove(id);
    }

    // 식사 상태 표시 로직
    // /food/status/complete/
    @PostMapping("/status/complete/{accessId}")
    public ResponseEntity<String> markAccessAsComplete(@PathVariable Long accessId) {
        // accessId를 사용하여 게시글 상태를 변경하는 로직을 호출
        service.changeComplete(accessId);
        return ResponseEntity.ok("게시글 상태가 complete로 변경되었습니다.");
    }
    // /food/status/match/
    @PostMapping("/status/match/{accessId}")
    public ResponseEntity<String> markAccessAsMatching(@PathVariable Long accessId) {
        service.changeMatching(accessId);
        return ResponseEntity.ok("게시글 상태가 matching으로 변경되었습니다.");
    }
}

