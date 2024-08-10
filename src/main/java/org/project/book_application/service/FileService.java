package org.project.book_application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.io.*;

@Service
public class FileService {
    public String uploadImage(MultipartFile bookImage) throws IOException {
        System.out.println(bookImage.isEmpty());
        if (!bookImage.isEmpty()) {
            String file = bookImage.getOriginalFilename();
            String fileName = generateImageName(file);
            var fos = new FileOutputStream("images" + File.separator + fileName);
            fos.write(bookImage.getBytes());
            fos.close();
            return fileName;
        }
        throw new RuntimeException("Image not found");
    }

    public String generateImageName(String file) {
        String extensionName = file.substring(file.lastIndexOf("."));
        var fileName = UUID.randomUUID().toString() + extensionName;
        return fileName;
    }

    public byte[] getImage(String imageName) throws IOException{
        var fis = new FileInputStream("images" + File.separator + imageName);
        byte[] image = fis.readAllBytes();
        fis.close();
        return image;
    }
}
