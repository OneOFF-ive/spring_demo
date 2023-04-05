package com.five.spring_demo.controller;

import com.five.spring_demo.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    R<String> upload(MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        String suffix = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;

        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basePath + fileName));
            return R.success("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.error("上传失败");
    }
}
