package com.innosync.hook.service;

import com.innosync.hook.dto.HackathonDto;
import com.innosync.hook.entity.HackathonEntity;
import com.innosync.hook.repository.HackathonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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

        //역순으로 데이터 반환
        Collections.reverse(hackathonDtos);

        Map<String, List<HackathonDto>> resultMap = new HashMap<>();
        resultMap.put("data", hackathonDtos);

        return resultMap;
    }

    @Override
    public Map<String, List<HackathonDto>> getAllMyContest(String username){
        List<HackathonEntity> result = hackathonRepository.findByWriterContaining(username);
        List<HackathonDto> recentHackathonDtos = result.stream()
                .map(this::entityToDTO).toList();
        Map<String, List<HackathonDto>> response = new HashMap<>();
        response.put("data", recentHackathonDtos);
        return response;
    }


    @Override
    public Map<String, Object> getAccessByTag(String tag) {
        List<HackathonEntity> result = hackathonRepository.findByStackContaining(tag);
        List<HackathonDto> recentHackathonDtos = result.stream()
                .map(this::entityToDTO).toList();
        Map<String, Object> response = new HashMap<>();
        response.put("data", recentHackathonDtos);
        return response;
    }
    @Override
    public Map<String, List<HackathonDto>> getRecentHackathons(int count) {
        List<HackathonEntity> topNHackathons = hackathonRepository.findTopNByOrderByRegDateDesc(count);
        List<HackathonDto> recentHackathonDtos = topNHackathons.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        Map<String, List<HackathonDto>> resultMap = new HashMap<>();
        resultMap.put("data", recentHackathonDtos);

        return resultMap;
    }


    @Override
    public Long hackathonRegister(HackathonDto dto, String name, String userName , Long id) {
        HackathonEntity hackathonEntity = dtoToEntity(dto);
        hackathonEntity.setWriter(name);
        hackathonEntity.setUserName(userName);
        hackathonEntity.setUserId(id);
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
            hackathonEntity.hackathonChangeStack(Collections.singletonList(String.join(",", dto.getStack())));
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
