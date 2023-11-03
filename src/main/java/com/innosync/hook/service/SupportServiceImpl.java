package com.innosync.hook.service;

import com.innosync.hook.dto.FCMNotificationRequestDto;
import com.innosync.hook.dto.SupportDto;
import com.innosync.hook.entity.HackathonEntity;
import com.innosync.hook.entity.SupportEntity;
import com.innosync.hook.repository.HackathonRepository;
import com.innosync.hook.repository.SupportRepository;
import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.req.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {
    private final HackathonRepository hackathonRepository;
    private final SupportRepository supportRepository;
    private final FCMNotificationService fcmNotificationService;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity applyToHackathon(Long hackathonId, SupportDto supportDto, Authentication authentication) {

        String userAccount = authentication.getName();

        Map<String, String> data = new HashMap<>();
        Optional<HackathonEntity> hackathonEntityOptional = hackathonRepository.findById(hackathonId);
        Optional<User> user = userRepository.findByUserAccount(userAccount);

        if (user.isEmpty() || hackathonEntityOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or Hackathon not found");
        }

        Long userId = user.get().getId();
        HackathonEntity hackathon = hackathonEntityOptional.get();

        // 중복 체크
        boolean isDuplicate = hackathon.getSupports().stream()
                .anyMatch(support -> support.getUserId().equals(userId));

        if (!isDuplicate) {
            Long targetUserId = hackathon.getUserId();
            String userName = supportDto.getApplicantName();
            FCMNotificationRequestDto fcmNotificationRequestDto = new FCMNotificationRequestDto(targetUserId, userName, supportDto.getIntroduction());
            fcmNotificationService.sendNotificationService(fcmNotificationRequestDto, userAccount, "p");

            // SupportEntity 생성 및 저장
            SupportEntity supportEntity = dtoToEntity(supportDto);
            supportEntity.setUserId(userId);
            supportEntity.setHackathon(hackathon);
            supportEntity = supportRepository.save(supportEntity);

            // HackathonEntity에 SupportEntity 추가
            hackathon.addSupport(supportEntity);
            hackathonRepository.save(hackathon); // HackathonEntity의 변경 사항을 저장
            data.put("Success" , "Success");
            return ResponseEntity.status(200).body(data);
        } else {
            // 중복 처리 로직
            data.put("Fail" , "Fail");
            return ResponseEntity.status(409).body(data);
        }
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
