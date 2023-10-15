package com.innosync.hook.controller;

import com.innosync.hook.entity.ImageEntity;
import com.innosync.hook.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @PostMapping("/saveImage")
    public String saveImage(@RequestBody ImageEntity image) {
        imageRepository.save(image);
        return "Image saved successfully!";
    }
}

