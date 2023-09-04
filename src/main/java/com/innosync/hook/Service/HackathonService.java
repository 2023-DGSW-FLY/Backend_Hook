package com.innosync.hook.Service;

import com.innosync.hook.constant.Status;
import com.innosync.hook.dto.HackathonDto;
import com.innosync.hook.entity.HackathonEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface HackathonService {

    // 모든 값 반환
    Map<String, List<HackathonDto>> getAllAccess();
    //tag 값 반환
    Map<String, Object> getAccessByTag(String tag);
    // C
    Long hackathonRegister(HackathonDto dto);
    // R
    Map<String, HackathonDto> hackathonRead(Long id);
    // U
    void hackathonModify(HackathonDto dto);
    // D
    void hackathonRemove(Long id);

    //상태 수정
    void changeComplete(Long accessId);
    void changeMatching(Long accessId);

    default HackathonEntity dtoToEntity(HackathonDto dto) {
        return HackathonEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .stack(String.join(",", dto.getStack())) // Convert the list to a comma-separated string
                .url(dto.getUrl())
                .status(dto.getStatus())
                .build();
    }

    default HackathonDto entityToDTO(HackathonEntity entity) {
        return HackathonDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .stack(Arrays.asList(entity.getStack().split(","))) // Convert the comma-separated string to a list
                .url(entity.getUrl())
                .status(Status.valueOf(String.valueOf(entity.getStatus())))
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }

}
