package com.innosync.hook.service;


import com.innosync.hook.aws.S3Uploader;
import com.innosync.hook.dto.UserDto;
import com.innosync.hook.exception.AppException;
import com.innosync.hook.exception.ErrorCode;
import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.req.Response;
import com.innosync.hook.req.User;
import com.innosync.hook.token.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final S3Uploader s3Uploader;

    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Transactional
    public User join(User user, MultipartFile image) throws IOException {
        if(!image.isEmpty()) {
            System.out.println("File set");
            String storedFileName = s3Uploader.upload(image,"images");
            user.addImageUrl(storedFileName);
        }
        userRepository.findByUserAccount(user.getUserAccount())
                .ifPresent(user1 -> {
                    throw new AppException(ErrorCode.DUPLICATED_USER_NAME);
                });
        userRepository.save(user);

        return user;
    }

    @Transactional
    public Map<String, String> login(String userAccount, String password, String firebaseToken) {
        User user = userRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
        if (!encoder.matches(password, user.getPassword())) {
            throw new AppException(ErrorCode.USER_NOT_FOUNDED);
        }


        user.addFirebaseToken(firebaseToken);
        userRepository.save(user);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", JwtTokenUtil.createAccessToken(userAccount, secretKey));
        tokens.put("refreshToken", JwtTokenUtil.createRefreshToken(userAccount, secretKey));

        return tokens;
    }


    public User getUserByUserAccount(String userAccount) {
        return userRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
    }

    public String imgUrlRedirect(Long id){
        if (userRepository.findById(id).get().getImageUrl()!=null){
            System.out.println(userRepository.findById(id).get().getImageUrl());
            return userRepository.findById(id).get().getImageUrl();
        } else {
            System.out.println("not image");
            return "https://hook-s3-innosync.s3.ap-northeast-2.amazonaws.com/images/initUserImage.png"; //기본 이미지
        }

    }
    @Transactional
    public void fixUserData(UserDto dto, String userName) {
        Optional<User> user = userRepository.findByUserAccount(userName);
        user.ifPresent(value -> value.fixUserData(
                dto.getUserAccount(),
                dto.getUser_name(),
                dto.getEmail(),
                dto.getUser_info(),
                dto.getGithub_url(),
                dto.getPortfolio_url()
        ));
        userRepository.save(user.get());
    }
    
    @Transactional
    public void fixUserImage(MultipartFile image, String userAccount) throws IOException {
        Optional<User> user = userRepository.findByUserAccount(userAccount);
        if(!image.isEmpty()) { // 만약 사진이 왔음 변경 요청이니 변경
            System.out.println("File set");
            String storedFileName = s3Uploader.upload(image,"images");
            user.get().addImageUrl(storedFileName);
        }
        else { // 사진이 없으면 기본이미지로 변경
            user.get().addImageUrl("https://hook-s3-innosync.s3.ap-northeast-2.amazonaws.com/images/initUserImage.png");
        }
    }

}
