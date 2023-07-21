package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.config.AppConstant;
import com.bikkadit.electronic.store.repository.ProductRepository;
import com.bikkadit.electronic.store.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstant.PRODUCT_URL)
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileService fileService;
}
