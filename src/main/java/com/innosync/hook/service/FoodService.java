package com.innosync.hook.service;

import com.innosync.hook.constant.Status;
import com.innosync.hook.dto.FoodDto;
import com.innosync.hook.entity.FoodEntity;

import java.util.List;
import java.util.Map;

public interface FoodService {

    // 모든 값 반환
    Map<String, List<FoodDto>> getAllAccess();
    // C
    Long foodRegister(FoodDto dto, String username);

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
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .place(dto.getPlace())
                .dateTime(dto.getDateTime())
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
                .dateTime(entity.getDateTime())
                .status(Status.valueOf(String.valueOf(entity.getStatus())))
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }

}

