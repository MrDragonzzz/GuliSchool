package com.ljy.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file);

    void removeAlyVideo(String id);

    void removeMoreVideo(List<String> videoIdList);
}
