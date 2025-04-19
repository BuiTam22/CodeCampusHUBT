package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.service.Cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("admin/api")
<<<<<<< HEAD:NCKH2024TQQD/src/main/java/com/codecampushubt/NCKH2024TQQD/rest/Restuploadimage.java
public class Restuploadimage {

=======
public class RestUploadImage {
>>>>>>> a7ec13d4f0cd7c72c38a21a682fa57608b50e484:NCKH2024TQQD/src/main/java/com/codecampushubt/NCKH2024TQQD/rest/RestUploadImage.java
    @Autowired
    private CloudinaryService cloudinaryService;
    @PostMapping("/upload")
    public ResponseEntity<Map<String,String>> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
            String imageUrl =cloudinaryService.uploadImage(file);
            if  (imageUrl != null){
                return ResponseEntity.ok().body(Map.of("url",imageUrl));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Message","File kooong hợp lệ"));
            }
    }
}
