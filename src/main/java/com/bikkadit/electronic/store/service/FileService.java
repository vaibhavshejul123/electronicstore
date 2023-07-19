package com.bikkadit.electronic.store.service;



import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String uploadImage(MultipartFile file , String Path) throws IOException;

    InputStream getResource(String path, String name) throws FileNotFoundException;
}
