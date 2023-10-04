package com.innosync.hook.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.innosync.hook.dto.FCMNotificationRequestDto;
import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.req.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class FCMNotificationService{
    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    public String sendNotificationService(FCMNotificationRequestDto requestDto){
        Optional<User> user = userRepository.findById(requestDto.getTargetUserId());
        if (user.isPresent()){
            if (user.get().getFirebaseToken() !=null){
                Notification notification = Notification.builder()
                        .setTitle(requestDto.getTitle())
                        .setBody(requestDto.getBody())
                        .build();
                Message message = Message.builder()
                        .setToken(user.get().getFirebaseToken())
                        .setNotification(notification)
                        .build();
                try {
                    firebaseMessaging.send(message);
                    return "알람을 성공적으로 보냈습니다. targetUserId = " + requestDto.getTargetUserId();
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                    return "알람을 보내기에 실패하였습니다. targetUserId = " + requestDto.getTargetUserId();
                }
            }
            else {
                return "서버에 저장된 해당유저의 firebaseToken이 존재하지 않습니다. targetUserId = " + requestDto.getTargetUserId();
            }
        }
        else {
            return "해당 유저가 존재하지 않습습니다. targetUserId = " + requestDto.getTargetUserId();
        }
    }
}
