package com.innosync.hook.service;

import com.innosync.hook.dto.ExerciseDto;
import com.innosync.hook.dto.SupportDto;
import com.innosync.hook.entity.HackathonEntity;
import com.innosync.hook.entity.SupportEntity;
import com.innosync.hook.repository.HackathonRepository;
import com.innosync.hook.repository.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {
    private final HackathonRepository hackathonRepository;
    private final SupportRepository supportRepository;

    @Override
    public Long applyToHackathon(Long hackathonId, SupportDto supportDto, Long userId) {
        Optional<HackathonEntity> hackathonEntityOptional = hackathonRepository.findById(hackathonId);
        if (hackathonEntityOptional.isPresent()) {
            HackathonEntity hackathon = hackathonEntityOptional.get();
            SupportEntity supportEntity = dtoToEntity(supportDto);
            supportEntity.setUserId(userId);
            hackathon.addSupport(supportEntity);
            supportEntity = supportRepository.save(supportEntity);
            return supportEntity.getId();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hackathon not found");
    }

    @Override
    public Map<String, List<SupportDto>> getSupportsForHackathon(Long hackathonId) {
        Optional<HackathonEntity> hackathonEntityOptional = hackathonRepository.findById(hackathonId);
        if (hackathonEntityOptional.isPresent()) {
            HackathonEntity hackathon = hackathonEntityOptional.get();
            List<SupportDto> supports = hackathon.getSupports().stream()
                    .map(this::entityToDto)
                    .collect(Collectors.toList());

            Map<String, List<SupportDto>> resultMap = new HashMap<>();
            resultMap.put("data", supports);
            return resultMap;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hackathon not found");
    }


    private SupportEntity dtoToEntity(SupportDto supportDto) {
        SupportEntity supportEntity = new SupportEntity();
        supportEntity.setApplicantName(supportDto.getApplicantName());
        supportEntity.setStudentId(supportDto.getStudentId());
        supportEntity.setContact(supportDto.getContact());
        supportEntity.setIntroduction(supportDto.getIntroduction());
        supportEntity.setPortfolioLink(supportDto.getPortfolioLink());
        return supportEntity;
    }

    private SupportDto entityToDto(SupportEntity supportEntity) {
        SupportDto supportDto = new SupportDto();
        supportDto.setId(supportEntity.getId());
        supportDto.setApplicantName(supportEntity.getApplicantName());
        supportDto.setStudentId(supportEntity.getStudentId());
        supportDto.setContact(supportEntity.getContact());
        supportDto.setIntroduction(supportEntity.getIntroduction());
        supportDto.setPortfolioLink(supportEntity.getPortfolioLink());
        supportDto.setUserId(supportEntity.getUserId());
        return supportDto;
    }

}
