package com.atguigu.gmall.product.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-26 09:27
 **/
public interface FileuploadService {
    /**
     * 文件上传
     * @param file
     * @return
     */
    String upload(MultipartFile file) throws Exception;
}
