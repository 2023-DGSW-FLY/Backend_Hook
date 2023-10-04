package com.innosync.hook.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FCMConfig {
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        //로컬용
        //ClassPathResource resource = new ClassPathResource("firebase/android-chat-fd2a6-firebase-adminsdk-i7qn9-8297a1f6dc.json");
        //배포용 (EC2)
        ClassPathResource resource = new ClassPathResource("/home/ubuntu/Backend_Hook/src/main/resources/firebase/android-chat-fd2a6-firebase-adminsdk-i7qn9-8297a1f6dc.json");
        InputStream refreshToken = resource.getInputStream();

        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();
        if ((firebaseAppList != null && !firebaseAppList.isEmpty())) {
            for (FirebaseApp app : firebaseAppList) {
                if (app.getName().equals(firebaseApp.DEFAULT_APP_NAME)) {
                    firebaseApp = app;
                }
            }

        }
        else {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
