package com.ximo.spring.security.sdk.demo.controller;

import com.ximo.spring.security.sdk.core.vo.ResultVO;
import com.ximo.spring.security.sdk.demo.dto.file.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.ximo.spring.security.sdk.demo.constants.FileConstants.ATTACHMENT;
import static com.ximo.spring.security.sdk.demo.constants.FileConstants.FOLDER;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description 文件相关控制器
 */
@Slf4j
@RequestMapping("/file")
@RestController
public class FileController {

    @PostMapping
    public ResultVO<FileInfo> uploadFile(MultipartFile file) throws IOException {
        log.info(file.getName());
        log.info(file.getOriginalFilename());
        log.info(file.getContentType());
        log.info(String.valueOf(file.getSize()));

        File localFile = new File(FOLDER, file.getOriginalFilename());
        //写入文件
        file.transferTo(localFile);
        return ResultVO.success(new FileInfo(localFile.getAbsolutePath()));
    }

    /**
     * 下载文件必须要有一个点
     *
     * @param fileName 文件名
     * @return ResponseEntity #{@link ResponseEntity}
     * @throws UnsupportedEncodingException 不支持的encoding操作
     */
    @GetMapping("{fileName:.+}")
    public ResponseEntity download(@PathVariable("fileName") String fileName) throws UnsupportedEncodingException {
        File file = FileUtils.getFile(FOLDER, fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                ATTACHMENT + new String(file.getName().getBytes("UTF-8"),
                        "ISO-8859-1") + "\"").body(file);
    }
}
