package com.innosync.hook.service;

import com.innosync.hook.constant.Status;
import com.innosync.hook.dto.ExerciseDto;
import com.innosync.hook.dto.FoodDto;
import com.innosync.hook.entity.FoodEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface FoodService {

    //자신이 작성한 모든글 반환
    Map<String, Object> getAllMyContest(Authentication authentication);

    // 모든 값 반환
    Map<String, List<FoodDto>> getAllAccess();

    // parameter 값으로 반환
    Map<String, List<FoodDto>> getRecentFood(int count);
    // C
    Long foodRegister(FoodDto dto, Authentication authentication);

    // R
    Map<String, FoodDto> foodRead(Long id);
    // U
    void foodModify(FoodDto dto);
    // D
    void foodRemove(Long id);

    //상태 수정
    void changeComplete(Long accessId);
    void changeMatching(Long accessId);

    default FoodEntity dtoToEntity(FoodDto dto) {
        return FoodEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .place(dto.getPlace())
                .foodName(dto.getFoodName())
                .status(dto.getStatus())
                .writer(dto.getWriter())
                .build();
    }

    default FoodDto entityToDTO(FoodEntity entity) {
        return FoodDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .place(entity.getPlace())
                .foodName(entity.getFoodName())
                .status(Status.valueOf(String.valueOf(entity.getStatus())))
                .writer(entity.getWriter())
                .userName(entity.getUserName())
                .userId(entity.getUserId())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }

}

