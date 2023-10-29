package com.innosync.hook.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.innosync.hook.dto.ExerciseDto;
import com.innosync.hook.dto.FCMNotificationRequestDto;
import com.innosync.hook.dto.FCMResponseDto;
import com.innosync.hook.dto.FoodDto;
import com.innosync.hook.entity.FCMEntity;
import com.innosync.hook.repository.FCMRepository;
import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.req.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class FCMNotificationService{
    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;
    private final FCMRepository fcmRepository;


    //알람 보내기 + 알람 내용 저장
    public Map<String, String> sendNotificationService(FCMNotificationRequestDto requestDto, String userAccount, String type){
        //System.out.println(requestDto.getBody() + "!!!!!이것은 바디여!!!!!");
        Map<String , String> result = new HashMap<>();
        result.put("Success" , "Success");
        Optional<User> user = userRepository.findById(requestDto.getTargetUserId());
        String targetUserAccount = user.get().getUserAccount();
        FCMEntity fcmEntity = new FCMEntity(requestDto.getTitle(), type, targetUserAccount);
        fcmRepository.save(fcmEntity);

        Optional<User> userOptional = userRepository.findByUserAccount(userAccount);
        User user1 = userOptional.get();
        Long useUserId = user1.getId();
        if (user.isPresent()){
            if (user.get().getFirebaseToken() !=null){
                Notification notification = Notification.builder()
                        .setTitle(requestDto.getTitle())
                        .setBody(requestDto.getBody())
                        .build();
                Message message = Message.builder()
                        .setToken(user.get().getFirebaseToken())
                        .setNotification(notification)
                        .putData("user", useUserId.toString()) // 이 부분 토큰 추출해서 유저 아이디 반환 (
                        .putData("type", type)// 0 -> 채팅, 1 -> 게시물
                        .build();
                try {
                    firebaseMessaging.send(message);
                    return result;
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                    return result;
                }
            }
            else {
                return result;
            }
        }
        else {
            return result;
        }
    }

    // 알람 내용 반환
    public Map<String , List<FCMResponseDto>> showDataFCM(String userAccount){
        List<FCMEntity> fcmEntities = fcmRepository.findByUserAccount(userAccount);
        List<FCMResponseDto> fcmResponseDtos = fcmEntities.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());

        Collections.reverse(fcmResponseDtos);

        Map<String, List<FCMResponseDto>> result = new HashMap<>();
        result.put("data" , fcmResponseDtos);
        return result;
    }
    FCMResponseDto entityToDto(FCMEntity fcmEntity){
        return FCMResponseDto.builder()
                .content(fcmEntity.getContent())
                .type(fcmEntity.getType())
                .regDate(fcmEntity.getRegDate())
                .build();
    }

}
