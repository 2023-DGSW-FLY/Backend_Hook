package com.innosync.hook.service;

import com.innosync.hook.dto.ExerciseDto;
import com.innosync.hook.dto.FoodDto;
import com.innosync.hook.entity.ExerciseEntity;
import com.innosync.hook.entity.FoodEntity;
import com.innosync.hook.entity.HackathonEntity;
import com.innosync.hook.repository.FoodRepository;
import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.req.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    @Override
    public Map<String , Object> getAllMyContest(Authentication authentication){
        String userName = authentication.getName();

        List<FoodEntity> result = foodRepository.findByWriterContaining(userName);
        List<FoodDto> foodDtos = result.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Collections.reverse(foodDtos);

        Map<String, Object> response = new HashMap<>();
        response.put("data", foodDtos);
        return response;
    }

    @Override
    public Map<String, List<FoodDto>> getAllAccess() {
        List<FoodEntity> foodEntities = foodRepository.findAll();
        List<FoodDto> foodDtos = foodEntities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Collections.reverse(foodDtos);

        Map<String, List<FoodDto>> resultMap = new HashMap<>();
        resultMap.put("data", foodDtos);

        return resultMap;
    }
    @Override
    public Map<String, List<FoodDto>> getRecentFood(int count) {
        List<FoodEntity> topNFood = foodRepository.findTopNByOrderByRegDateDesc(count);
        List<FoodDto> recentFoodDtos = topNFood.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());


        Map<String, List<FoodDto>> resultMap = new HashMap<>();
        resultMap.put("data", recentFoodDtos);

        return resultMap;
    }

    @Override
    public Long foodRegister(FoodDto dto , Authentication authentication) {

        String userAccount = authentication.getName();
        Optional<User> userOptional = userRepository.findByUserAccount(userAccount);
        User user = userOptional.get(); // User정보 받아서
        Long userId = user.getId(); //정보중 user_id 만 추출하여 userId에 저장
        String userName = user.getUser_name();

        FoodEntity foodEntity = dtoToEntity(dto);
        foodEntity.setWriter(userAccount);
        foodEntity.setUserName(userName);
        foodEntity.setUserId(userId);
        foodRepository.save(foodEntity);
        return foodEntity.getId();
    }

    @Override
    public Map<String, FoodDto> foodRead(Long id) {
        Optional<FoodEntity> result = foodRepository.findById(id);
        Map<String, FoodDto> resultMap = new HashMap<>();

        resultMap.put("data", entityToDTO(result.get()));

        return resultMap;
    }

    @Override
    public void foodModify(FoodDto dto) {
        Optional<FoodEntity> result = foodRepository.findById(dto.getId());
        if (result.isPresent()) {
            FoodEntity foodEntity = result.get();
            foodEntity.setFoodName(dto.getFoodName());
            foodEntity.setTitle(dto.getContent());
            foodEntity.setContent(dto.getContent());
            foodEntity.setPlace(dto.getPlace());
            foodRepository.save(foodEntity);
        }
    }

    @Override
    public void changeComplete(Long accessId) {
        Optional<FoodEntity> accessEntityOptional = foodRepository.findById(accessId);
        if (accessEntityOptional.isPresent()) {
            FoodEntity foodEntity = accessEntityOptional.get();
            foodEntity.setStatusComplete();
            foodRepository.save(foodEntity);
        }
    }

    @Override
    public void changeMatching(Long accessId) {
        Optional<FoodEntity> accessEntityOptional = foodRepository.findById(accessId);
        if (accessEntityOptional.isPresent()) {
            FoodEntity foodEntity = accessEntityOptional.get();
            foodEntity.setStatusMatching();
            foodRepository.save(foodEntity);
        }
    }

    @Override
    public void foodRemove(Long id) {
        foodRepository.deleteById(id);
    }
}
