package com.innosync.hook.controller;

import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.req.User;
import com.innosync.hook.service.FoodService;
import com.innosync.hook.dto.FoodDto;
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

@RequestMapping("/food")
@RestController
@RequiredArgsConstructor
public class FoodController {

    private final FoodService service;


    //자신이 작성한 모든 글 받아오기
    @GetMapping("/all")
    public Map<String, Object> getAllMyContest(Authentication authentication){
        return service.getAllMyContest(authentication);
    }

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
    public Map<String, String> register(
            @RequestBody FoodDto dto , Authentication authentication

    ){

        service.foodRegister(dto,authentication);
        Map<String, String> data = new HashMap<>();
        data.put("Success" , "Success");
        return data;
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

    // 음식 상태 표시 로직
    // /food/status/complete/id
    @PostMapping("/status/complete/{foodId}")
    public Map<String,String> markComplete(@PathVariable Long foodId) {
        // accessId를 사용하여 게시글 상태를 변경하는 로직을 호출
        service.changeComplete(foodId);
        Map<String, String> data = new HashMap<>();
        data.put("Success" , "Success");

        return data;
    }
    // /food/status/match/id
    @PostMapping("/status/match/{foodId}")
    public Map<String, String> markMatching(@PathVariable Long foodId) {
        service.changeMatching(foodId);
        Map<String, String> data = new HashMap<>();
        data.put("Success" , "Success");

        return data;
    }
}

