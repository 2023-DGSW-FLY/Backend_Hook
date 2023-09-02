package com.innosync.hook.Service;

import com.innosync.hook.dto.AccessDto;
import com.innosync.hook.dto.ContestDto;
import com.innosync.hook.entity.AccessEntity;
import com.innosync.hook.entity.ContestEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface AccessService {

    //get all
    Map<String, List<AccessDto>> getAllAccess();


    // C
    Long register(AccessDto dto);
    // R
    AccessDto read(Long id);
    // U
    void modify(AccessDto dto);
    // D
    void remove(Long gno);

    default AccessEntity dtoToEntity(AccessDto dto) {
        return AccessEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .stack(dto.getStack())
                .content(dto.getContent())
                .url(dto.getUrl())
                .build();
    }

    default AccessDto entityToDTO(AccessEntity entity) {
        return AccessDto.builder()
                .id(entity.getId())
                .stack(entity.getStack())
                .name(entity.getName())
                .content(entity.getContent())
                .url(entity.getUrl())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }

}
