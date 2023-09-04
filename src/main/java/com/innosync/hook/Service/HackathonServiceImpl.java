package com.innosync.hook.Service;

import com.innosync.hook.dto.AccessDto;
import com.innosync.hook.dto.HackathonDto;
import com.innosync.hook.entity.AccessEntity;
import com.innosync.hook.entity.HackathonEntity;
import com.innosync.hook.repository.HackathonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HackathonServiceImpl implements HackathonService{

    private final HackathonRepository hackathonRepository;

    @Override
    public Map<String , List<HackathonDto>> getAllAccess() {
        List<HackathonEntity> hackathonEntities = hackathonRepository.findAll();
        List<HackathonDto> hackathonDtos = hackathonEntities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Map<String, List<HackathonDto>> resultMap = new HashMap<>();
        resultMap.put("data", hackathonDtos);

        return resultMap;
    }
    @Override
    public Long hackathonRegister(HackathonDto dto) {
        HackathonEntity hackathonEntity = dtoToEntity(dto);
        hackathonRepository.save(hackathonEntity);
        return hackathonEntity.getId();
    }

    @Override
    public Map<String, HackathonDto> hackathonRead(Long id) {
        Optional<HackathonEntity> result = hackathonRepository.findById(id);
        Map<String, HackathonDto> resultMap = new HashMap<>();

        resultMap.put("data", entityToDTO(result.get()));

        return resultMap;
    }

    @Override
    public void hackathonModify(HackathonDto dto) {
        Optional<HackathonEntity> result = hackathonRepository.findById(dto.getId());
        if (result.isPresent()) {
            HackathonEntity hackathonEntity = result.get();
            hackathonEntity.hackathonChangeTitle(dto.getContent());
            hackathonEntity.hackathonChangeContent(dto.getUrl());
            hackathonEntity.hackathonChangeStack(dto.getStack());
            hackathonEntity.hackathonChangeUrl(dto.getUrl());
            hackathonRepository.save(hackathonEntity);
        }
    }

    @Override
    public void changeComplete(Long accessId) {
        Optional<HackathonEntity> accessEntityOptional = hackathonRepository.findById(accessId);
        if (accessEntityOptional.isPresent()) {
            HackathonEntity hackathonEntity = accessEntityOptional.get();
            hackathonEntity.hackathonChangeComplete();
            hackathonRepository.save(hackathonEntity);
        }
    }

    @Override
    public void changeMatching(Long accessId) {
        Optional<HackathonEntity> accessEntityOptional = hackathonRepository.findById(accessId);
        if (accessEntityOptional.isPresent()) {
            HackathonEntity hackathonEntity = accessEntityOptional.get();
            hackathonEntity.hackathonChangeMatching();
            hackathonRepository.save(hackathonEntity);
        }
    }

    @Override
    public void hackathonRemove(Long id) {
        hackathonRepository.deleteById(id);
    }
}
