package com.example.elibrary.service.serviceImpl;

import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.model.File;
import com.example.elibrary.repository.FileRepository;
import com.example.elibrary.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.example.elibrary.service.validator.AppStatusCode.DATABASE_ERROR_CODE;
import static com.example.elibrary.service.validator.AppStatusCode.OK_CODE;
import static com.example.elibrary.service.validator.AppStatusMessages.DATABASE_ERROR;
import static com.example.elibrary.service.validator.AppStatusMessages.OK;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    @Override
    public ResponseDto<Integer> fileUpload(MultipartFile file) {
        File fileEntity = new File();
        fileEntity.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        fileEntity.setCreatedAt(LocalDateTime.now());

        try {
            String filePath;
            Files.copy(file.getInputStream(), Path.of(filePath = filePath("upload",fileEntity.getExt())));
            fileEntity.setPath(filePath);
            File savedFile = fileRepository.save(fileEntity);

            return ResponseDto.<Integer>builder()
                    .data(savedFile.getId())
                    .message(OK)
                    .success(true)
                    .build();
        } catch (IOException e) {
            log.error("Error while saving file: {}", e.getMessage());
            return ResponseDto.<Integer>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Byte[]> getBook(Integer id) {
        return null;
    }

    public static String filePath(String folder,String ext){
        LocalDate localDate = LocalDate.now();
        String path = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        java.io.File file = new java.io.File(folder + "/"+ path);
        if (!file.exists()){
            file.mkdirs();
        }
        String uuid = UUID.randomUUID().toString();
        return file.getPath() + "\\"+ uuid + ext;
    }
}
