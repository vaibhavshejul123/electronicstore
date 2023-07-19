package com.bikkadit.electronic.store.exception;

import com.bikkadit.electronic.store.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BadApiRequest extends RuntimeException{

    public BadApiRequest(String message){
        super(message);
    }
    public BadApiRequest(){
        super("Bad Request !!");
    }


}
