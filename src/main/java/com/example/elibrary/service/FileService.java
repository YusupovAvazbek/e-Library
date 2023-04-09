package com.example.elibrary.service;

import com.example.elibrary.dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ResponseDto<Integer> fileUpload(MultipartFile file);
    ResponseDto<Byte[]> getBook(Integer id);
}
