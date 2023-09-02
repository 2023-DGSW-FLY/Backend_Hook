package com.innosync.hook.Service;

import com.innosync.hook.dto.AccessDto;
import com.innosync.hook.dto.ContestDto;
import com.innosync.hook.entity.AccessEntity;
import com.innosync.hook.entity.ContestEntity;
import com.innosync.hook.repository.AccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class AccessServiceImpl implements AccessService {
    private final AccessRepository accessRepository;

    @Override
    public Map<String , List<AccessDto>> getAllAccess() {
        List<AccessEntity> accessEntities = accessRepository.findAll();
        List<AccessDto> accessDtos = accessEntities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Map<String, List<AccessDto>> resultMap = new HashMap<>();
        resultMap.put("data", accessDtos);

        return resultMap;
    }

    @Override
    public Long register(AccessDto dto) {
        log.info("====================");
        log.info("GuestBook DTO : {}", dto);
        AccessEntity accessEntity = dtoToEntity(dto);
        accessRepository.save(accessEntity);
        return accessEntity.getId();
    }

    @Override
    public AccessDto read(Long id) {
        Optional<AccessEntity> result = accessRepository.findById(id);
        return (result.isPresent()) ? entityToDTO(result.get()) : null;
    }

    @Override
    public void modify(AccessDto dto) {
        Optional<AccessEntity> result = accessRepository.findById(dto.getId());
        if (result.isPresent()) {
            AccessEntity accessEntity = result.get();
            accessEntity.changeContent(dto.getContent());
            accessEntity.changeUrl(dto.getUrl());
            accessRepository.save(accessEntity);
        }
    }


    @Override
    public void remove(Long id) {
        accessRepository.deleteById(id);
    }

}