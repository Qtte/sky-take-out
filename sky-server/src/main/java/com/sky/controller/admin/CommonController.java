package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用类
 */
@Slf4j
@RestController
@RequestMapping("admin/common")
public class CommonController {
    @Autowired
    AliOssUtil aliOssUtil;
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        try {
            //获取文件原始名
            String originalFilename = file.getOriginalFilename();
            //获取文件名后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            //构建新文件名
            String objectName = UUID.randomUUID() + extension;

            //文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(),objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败,{}",e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}