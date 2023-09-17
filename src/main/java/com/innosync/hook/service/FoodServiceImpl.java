package com.innosync.hook.service;

import com.innosync.hook.dto.FoodDto;
import com.innosync.hook.entity.FoodEntity;
import com.innosync.hook.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Override
    public Map<String, List<FoodDto>> getAllAccess() {
        List<FoodEntity> foodEntities = foodRepository.findAll();
        List<FoodDto> foodDtos = foodEntities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Map<String, List<FoodDto>> resultMap = new HashMap<>();
        resultMap.put("data", foodDtos);

        return resultMap;
    }

    @Override
    public Map<String, Object> getAccessByTag(String tag) {
        List<FoodEntity> result = foodRepository.findByStackContaining(tag);
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        return response;
    }

    @Override
    public Long foodRegister(FoodDto dto) {
        FoodEntity foodEntity = dtoToEntity(dto);
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
            foodEntity.setDateTime(dto.getDateTime());
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
