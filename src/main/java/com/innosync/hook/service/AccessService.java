package com.innosync.hook.service;

import com.innosync.hook.constant.Status;
import com.innosync.hook.dto.AccessDto;
import com.innosync.hook.dto.HackathonDto;
import com.innosync.hook.entity.AccessEntity;

import java.util.List;
import java.util.Map;

public interface AccessService {

    //get all
    Map<String, List<AccessDto>> getAllAccess();
    // parameter 값으로
    Map<String, List<AccessDto>> getRecentAccess(int count);

    //tag 값 반환
    Map<String, Object> getAccessByTag(String tag);

    void changeComplete(Long accessId);
    void changeMatching(Long accessId);

    // C
    Long register(AccessDto dto, String username, Long userId);
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
                .status(Status.valueOf(dto.getStatus()))
                .build();
    }

    default AccessDto entityToDTO(AccessEntity entity) {
        return AccessDto.builder()
                .id(entity.getId())
                .stack(entity.getStack())
                .name(entity.getName())
                .content(entity.getContent())
                .url(entity.getUrl())
                .status(String.valueOf(entity.getStatus()))
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }

}
