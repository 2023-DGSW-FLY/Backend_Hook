package com.innosync.hook.controller;

import com.innosync.hook.repository.UserRepository;
import com.innosync.hook.service.UserService;
import com.innosync.hook.req.*;
import com.innosync.hook.token.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @Value("${jwt.token.secret}")
    private String secretKey;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final UserRepository repository;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestPart("data") UserJoinRequest userJoinRequest, @RequestPart("image") MultipartFile file) throws IOException {
//        System.out.println("name" + userJoinRequest.getUserName());
//        System.out.println("pwd" + userJoinRequest.getPassword());
        String encodedPassword = encoder.encode(userJoinRequest.getPassword());
        User user = new User(userJoinRequest.getUserAccount(), encodedPassword, userJoinRequest.getUserName(), userJoinRequest.getEmail(), userJoinRequest.getUser_info(), userJoinRequest.getGithub_url(),userJoinRequest.getGithub_url());
        userService.join(user, file);
        UserJoinResponse userJoinResponse = new UserJoinResponse(user.getUserAccount());

        return Response.success(userJoinResponse);
    }



    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserLoginRequest userLoginRequest) {
        Map<String, String> tokenMap = userService.login(userLoginRequest.getUserAccount(), userLoginRequest.getPassword(), userLoginRequest.getFireBaseToken());

        Map<String, Object> response = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        data.put("accessToken", tokenMap.get("accessToken"));
        data.put("refreshToken", tokenMap.get("refreshToken"));

        response.put("data", data);

        return response;
    }




    @GetMapping("/user")
    public Map<String, Object> profile(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = repository.findByUserAccount(username);
        User user = userOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")); //람다식으로 예외처리
        Map<String, Object> response = new HashMap<>();
        response.put("data" , user);
        return response;
    }



    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshAccessToken(@RequestBody RefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();

        // 리프레쉬 토큰 검증
        if (!JwtTokenUtil.isRefreshTokenValid(refreshToken, secretKey)) {
            System.out.println("토큰 잘못됨" + refreshToken);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "403");
            response.put("message", "유효하지 않은 토큰.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // 리프레쉬 토큰으로부터 어세스 토큰 생성
        String newAccessToken = JwtTokenUtil.generateAccessTokenFromRefreshToken(refreshToken, secretKey);

        Map<String, Object> response = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        data.put("accessToken", newAccessToken);

        response.put("data", data);



        return ResponseEntity.ok(response);
    }

    @GetMapping("/image/{id}")
    public RedirectView imgUrlRedirect(@PathVariable("id") Long id) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(userService.imgUrlRedirect(id));
        return redirectView;
    }
    @GetMapping("/user/{userId}")
    public Map<String, Object> toUserId(@PathVariable("userId") Long id){
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("name", repository.findById(id).get().getUser_name());
        Map<String, Object> map = new HashMap<>();
        map.put("data", resultMap);
        return map;
    }
}