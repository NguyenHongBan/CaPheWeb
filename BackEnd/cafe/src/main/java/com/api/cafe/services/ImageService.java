package com.api.cafe.services;

import com.api.cafe.models.Image;
import com.api.cafe.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final IStorageService storageService;
    private final ImageRepository imageRepository;

    public CafeResponse uploadFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                final var generatedFileName = storageService.storeFile(file);
                final var image = new Image(generatedFileName);
                return CafeResponse.builder().data(imageRepository.save(image)).build();
            } catch (Exception e) {
                return CafeResponse.builder().build();
            }
        }
        return CafeResponse.builder().build();
    }
}
