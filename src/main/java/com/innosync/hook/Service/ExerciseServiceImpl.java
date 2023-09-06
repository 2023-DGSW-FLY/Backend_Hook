package com.innosync.hook.Service;

import com.innosync.hook.dto.ExerciseDto;
import com.innosync.hook.dto.HackathonDto;
import com.innosync.hook.entity.ExerciseEntity;
import com.innosync.hook.entity.HackathonEntity;
import com.innosync.hook.repository.ExerciseRepository;
import com.innosync.hook.repository.HackathonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;

    @Override
    public Map<String, List<ExerciseDto>> getAllAccess() {
        List<ExerciseDto> exerciseEntities = exerciseRepository.findAll();
        List<ExerciseDto> exerciseDtos = exerciseEntities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Map<String, List<HackathonDto>> resultMap = new HashMap<>();
        resultMap.put("data", exerciseDtos);

        return resultMap;
    }

    @Override
    public Map<String, Object> getAccessByTag(String tag) {
        List<ExerciseEntity> result = exerciseRepository.findByStackContaining(tag);
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        return response;
    }

    @Override
    public Long hackathonRegister(HackathonDto dto) {
        ExerciseEntity exerciseEntity = dtoToEntity(dto);
        exerciseRepository.save(exerciseEntity);
        return exerciseEntity.getId();
    }

    @Override
    public Map<String, ExerciseDto> exerciseRead(Long id) {
        Optional<ExerciseEntity> result = exerciseRepository.findById(id);
        Map<String, ExerciseDto> resultMap = new HashMap<>();

        resultMap.put("data", entityToDTO(result.get()));

        return resultMap;
    }

    @Override
    public void exerciseModify(ExerciseDto dto) {
        Optional<ExerciseEntity> result = exerciseRepository.findById(dto.getId());
        if (result.isPresent()) {
            ExerciseEntity exerciseEntity = result.get();
            exerciseEntity.setTitle(dto.getContent());
            exerciseEntity.setContent(dto.getContent());
            exerciseEntity.setLocation(dto.getLocatioin());
            exerciseRepository.save(exerciseEntity);
        }
    }

    @Override
    public void changeComplete(Long accessId) {
        Optional<HackathonEntity> accessEntityOptional = exerciseRepository.findById(accessId);
        if (accessEntityOptional.isPresent()) {
            ExerciseEntity exerciseEntity = accessEntityOptional.get();
            exerciseEntity.setStatusComplete();
            exerciseRepository.save(exerciseEntity);
        }
    }

    @Override
    public void changeMatching(Long accessId) {
        Optional<ExerciseEntity> accessEntityOptional = exerciseRepository.findById(accessId);
        if (accessEntityOptional.isPresent()) {
            ExerciseEntity exerciseEntity = accessEntityOptional.get();
            exerciseEntity.setStatusMatching();
            exerciseRepository.save(exerciseEntity);
        }
    }

    @Override
    public void exerciseRemove(Long id) {
        exerciseRepository.deleteById(id);
    }
}
