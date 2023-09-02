package com.innosync.hook.Service;

import com.innosync.hook.dto.AccessDto;
import com.innosync.hook.entity.AccessEntity;

import java.util.List;
import java.util.Map;

public interface AccessService {

    //get all
    Map<String, List<AccessDto>> getAllAccess();

    //tag 값 반환
    Map<String, Object> getAccessByTag(String tag);

    // C
    Long register(AccessDto dto);
    // R
    Map<String, AccessDto> read(Long id);
    // U
    void modify(AccessDto dto);
    // D
    void remove(Long id);

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
