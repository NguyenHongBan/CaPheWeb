package com.api.cafe.services;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    public String storeFile(MultipartFile file);

    public byte[] readFileContent(String fileName);
}
