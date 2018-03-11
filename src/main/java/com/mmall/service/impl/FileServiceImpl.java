package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Endos on 2018/3/11.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String uploda(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();// 原名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1); // 扩展名
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;// 新文件名
        logger.info("开始上传文件，上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            // 文件已经上传成功了
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // 文件已经上传到ftp服务器上
            targetFile.delete();
        } catch (IOException e) {
            logger.error("文件上传异常", e);
            return null;
        }
        return targetFile.getName();
    }
}
