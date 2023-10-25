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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class FCMNotificationService{
    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    public Map<String, String> sendNotificationService(FCMNotificationRequestDto requestDto, String userName, String type){
        //System.out.println(requestDto.getBody() + "!!!!!이것은 바디여!!!!!");
        Map<String , String> result = new HashMap<>();
        result.put("Success" , "Success");
        Optional<User> user = userRepository.findById(requestDto.getTargetUserId());
        Optional<User> userOptional = userRepository.findByUserAccount(userName);
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
}
