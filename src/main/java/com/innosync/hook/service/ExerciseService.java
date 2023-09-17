package com.innosync.hook.service;

import com.innosync.hook.constant.Status;
import com.innosync.hook.dto.ExerciseDto;
import com.innosync.hook.entity.ExerciseEntity;

import java.util.List;
import java.util.Map;

public interface ExerciseService {

    // 모든 값 반환
    Map<String, List<ExerciseDto>> getAllAccess();
    // C
    Long exerciseRegister(ExerciseDto dto);

    //Map<String, Object> getAccessByTag(String tag);

    // R
    Map<String, ExerciseDto> exerciseRead(Long id);
    // U
    void exerciseModify(ExerciseDto dto);
    // D
    void exerciseRemove(Long id);

    //상태 수정
    void changeComplete(Long accessId);
    void changeMatching(Long accessId);

    default ExerciseEntity dtoToEntity(ExerciseDto dto) {
        return ExerciseEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .place(dto.getPlace())
                .dateTime(dto.getDateTime())
                .status(dto.getStatus())
                .build();
    }

    default ExerciseDto entityToDTO(ExerciseEntity entity) {
        return ExerciseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .place(entity.getPlace())
                .dateTime(entity.getDateTime())
                .status(Status.valueOf(String.valueOf(entity.getStatus())))
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }

}
