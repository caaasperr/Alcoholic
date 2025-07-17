package com.caaasperr.Alcoholic._common.image;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageHandler {
    private final String uploadDirectory = Paths.get(System.getProperty("user.dir"), "upload").toString();

    public String saveImage(MultipartFile image) throws IOException {
        System.out.println(uploadDirectory);
        if (image.isEmpty()) {
            return null;
        }

        String fileName = image.getOriginalFilename();
        Path filePath = Paths.get(uploadDirectory, fileName);

        if(!Files.exists(Paths.get(uploadDirectory))) {
            Files.createDirectories(Paths.get(uploadDirectory));
        }

        Files.write(filePath, image.getBytes());

        return filePath.toString();
    }
}
