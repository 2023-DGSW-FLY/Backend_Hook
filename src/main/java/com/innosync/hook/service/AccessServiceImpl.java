package com.innosync.hook.service;

import com.innosync.hook.dto.AccessDto;
import com.innosync.hook.dto.ExerciseDto;
import com.innosync.hook.dto.HackathonDto;
import com.innosync.hook.entity.AccessEntity;
import com.innosync.hook.entity.ExerciseEntity;
import com.innosync.hook.repository.AccessRepository;
import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.req.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AccessServiceImpl implements AccessService {
    private final AccessRepository accessRepository;
    private final UserRepository userRepository;


    @Override
    public Map<String , List<AccessDto>> getAllMyContest(Authentication authentication){
        List<AccessEntity> result = accessRepository.findByNameContaining(authentication.getName());
        List<AccessDto> Access = result.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Collections.reverse(Access);

        Map<String, List<AccessDto>> response = new HashMap<>();
        response.put("data", Access);
        return response;
    }

    @Override
    public Map<String , List<AccessDto>> getAllAccess() {
        List<AccessEntity> accessEntities = accessRepository.findAll();
        List<AccessDto> accessDtos = accessEntities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Map<String, List<AccessDto>> resultMap = new HashMap<>();
        resultMap.put("data", accessDtos);

        Collections.reverse(accessDtos);

        return resultMap;
    }

    @Override
    public Map<String, List<AccessDto>> getRecentAccess(int count) {
        List<AccessEntity> topNAccasses = accessRepository.findTopNByOrderByRegDateDesc(count);
        List<AccessDto> recentAccessDtos = topNAccasses.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Map<String, List<AccessDto>> resultMap = new HashMap<>();
        resultMap.put("data", recentAccessDtos);

        return resultMap;
    }
    @Override
    public Map<String, Object> getAccessByTag(String tag) {
        List<AccessEntity> result = accessRepository.findByStackContaining(tag);
        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        return response;
    }

    @Override
    public void changeComplete(Long accessId) {
        Optional<AccessEntity> accessEntityOptional = accessRepository.findById(accessId);
        if (accessEntityOptional.isPresent()) {
            AccessEntity accessEntity = accessEntityOptional.get();
            accessEntity.changeComplete();
            accessRepository.save(accessEntity);
        }
    }

    @Override
    public void changeMatching(Long accessId) {
        Optional<AccessEntity> accessEntityOptional = accessRepository.findById(accessId);
        if (accessEntityOptional.isPresent()) {
            AccessEntity accessEntity = accessEntityOptional.get();
            accessEntity.changeMatching();
            accessRepository.save(accessEntity);
        }
    }


    @Override
    public Long register(AccessDto dto , Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUserAccount(username);
        Long userId = userOptional.get().getId();
        AccessEntity accessEntity = dtoToEntity(dto);
        accessEntity.setName(username);
        accessEntity.setUserId(userId);
        accessRepository.save(accessEntity);
        return accessEntity.getId();
    }

    @Override
    public Map<String, AccessDto> read(Long id) {
        Optional<AccessEntity> result = accessRepository.findById(id);
        Map<String, AccessDto> resultMap = new HashMap<>();

        resultMap.put("data", entityToDTO(result.get()));

        return resultMap;
    }

    @Override
    public void modify(AccessDto dto) {
        Optional<AccessEntity> result = accessRepository.findById(dto.getId());
        if (result.isPresent()) {
            AccessEntity accessEntity = result.get();
            accessEntity.changeContent(dto.getContent());
            accessEntity.changeStack(dto.getStack());
            accessEntity.changeUrl(dto.getUrl());
            accessRepository.save(accessEntity);
        }
    }


    @Override
    public void remove(Long id) {
        accessRepository.deleteById(id);
    }

}