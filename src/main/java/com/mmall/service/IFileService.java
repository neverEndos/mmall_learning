package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Endos on 2018/3/11.
 */
public interface IFileService {

    String uploda(MultipartFile file, String path);
}
