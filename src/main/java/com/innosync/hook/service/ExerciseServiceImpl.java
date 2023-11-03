package com.innosync.hook.service;

import com.innosync.hook.dto.ExerciseDto;
import com.innosync.hook.entity.ExerciseEntity;
import com.innosync.hook.entity.FoodEntity;
import com.innosync.hook.repository.ExerciseRepository;
import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.req.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    @Override
    public Map<String , Object> getAllMyContest(Authentication authentication){
        List<ExerciseEntity> result = exerciseRepository.findByWriterContaining(authentication.getName());
        List<ExerciseDto> exerciseDtos = result.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Collections.reverse(exerciseDtos);

        Map<String, Object> response = new HashMap<>();
        response.put("data", exerciseDtos);
        return response;
    }

    @Override
    public Map<String, List<ExerciseDto>> getAllAccess() {
        List<ExerciseEntity> exerciseEntities = exerciseRepository.findAll();
        List<ExerciseDto> exerciseDtos = exerciseEntities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Collections.reverse(exerciseDtos);

        Map<String, List<ExerciseDto>> resultMap = new HashMap<>();
        resultMap.put("data", exerciseDtos);

        return resultMap;
    }

    @Override
    public Map<String, List<ExerciseDto>> getRecentExercise(int count) {
        List<ExerciseEntity> topNExercise = exerciseRepository.findTopNByOrderByRegDateDesc(count);
        List<ExerciseDto> recentAccessDtos = topNExercise.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Map<String, List<ExerciseDto>> resultMap = new HashMap<>();
        resultMap.put("data", recentAccessDtos);

        return resultMap;
    }

    @Override
    public Map<String, Object> getAccessByTag(String tag) {
        List<ExerciseEntity> result = exerciseRepository.findByExerciseContaining(tag);
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        return response;
    }

    @Override
    public Long exerciseRegister(ExerciseDto dto, Authentication authentication) {

        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUserAccount(username);
        User user = userOptional.get(); // User정보 받아서
        Long userId = user.getId(); //정보중 user_id 만 추출하여 userId에 저장
        String userName = user.getUser_name(); // 유저 실명
        String userAccount = user.getUserAccount(); // 유저 아이디

        ExerciseEntity exerciseEntity = dtoToEntity(dto);
        exerciseEntity.setWriter(userAccount);
        exerciseEntity.setUserName(userName);
        exerciseEntity.setUserId(userId);
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
            exerciseEntity.setDateTime(dto.getDateTime());
            exerciseEntity.setExercise(dto.getExercise());
            exerciseEntity.setPlace(dto.getPlace());
            exerciseRepository.save(exerciseEntity);
        }
    }

    @Override
    public void changeComplete(Long accessId) {
        Optional<ExerciseEntity> accessEntityOptional = exerciseRepository.findById(accessId);
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
