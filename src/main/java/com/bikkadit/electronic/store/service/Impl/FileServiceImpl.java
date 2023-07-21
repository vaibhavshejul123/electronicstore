package com.bikkadit.electronic.store.service.Impl;

import com.bikkadit.electronic.store.exception.BadApiRequest;
import com.bikkadit.electronic.store.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);


    @Override
    public String uploadImage(String Path, MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName :{}", originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWithFileName = Path + fileNameWithExtension;

        logger.info("image name with full path {}", fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            File folder = new File(Path);
            logger.info("file extension {}", extension);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;
        } else {
            throw new BadApiRequest("File with this" + extension + "not allowed !!");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath = path + File.separator;

        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
