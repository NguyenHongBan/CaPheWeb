package com.api.cafe.controllers;

import com.api.cafe.services.CafeResponse;
import com.api.cafe.services.IStorageService;
import com.api.cafe.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "api/image")
@RequiredArgsConstructor
public class ImageController {
    private final IStorageService storageService;
    private final ImageService imageService;

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @PostMapping
    public ResponseEntity<CafeResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            logger.info("Upload file thành công.");
            return ResponseEntity.ok(imageService.uploadFile(file));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CafeResponse("Xảy ra lỗi.", e.getMessage()));
        }
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            logger.info("Get file thành công.");
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
}
