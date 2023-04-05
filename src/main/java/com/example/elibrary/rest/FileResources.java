package com.example.elibrary.rest;

import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileResources {
    private final FileService fileService;
    @PostMapping
    public ResponseDto<Integer> uploadFile(@RequestPart("bookFile") MultipartFile bookFile){
        return fileService.fileUpload(bookFile);
    }

}
