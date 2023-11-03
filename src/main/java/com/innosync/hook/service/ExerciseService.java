package com.innosync.hook.service;

import com.innosync.hook.constant.Status;
import com.innosync.hook.dto.ExerciseDto;
import com.innosync.hook.entity.ExerciseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface ExerciseService {


    //자신이 작성한 모든글 반환
    Map<String, Object> getAllMyContest(Authentication authentication);

    // 모든 값 반환
    Map<String, List<ExerciseDto>> getAllAccess();

    // parameter 값으로 반환
    Map<String, List<ExerciseDto>> getRecentExercise(int count);

    // C
    Long exerciseRegister(ExerciseDto dto, Authentication authentication);

    Map<String, Object> getAccessByTag(String tag);

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
                .title(dto.getTitle())
                .content(dto.getContent())
                .place(dto.getPlace())
                .exercise(dto.getExercise())
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
                .exercise(entity.getExercise())
                .dateTime(entity.getDateTime())
                .userName(entity.getUserName())
                .writer(entity.getWriter())
                .userId(entity.getUserId())
                .status(Status.valueOf(String.valueOf(entity.getStatus())))
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }

}
