package com.example.ecomm.ecommercee.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //files names of curent or original file
        String originalFileName = file.getOriginalFilename();

        //generating a unique file name
        String randomId = UUID.randomUUID().toString();
        //mat.jpg --> 1234--> 1234.jpg
        String  fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path+ File.separator + fileName; //

        //check if images folder path exist and if not create
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        //upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        //return file name
        return fileName;
    }
}
