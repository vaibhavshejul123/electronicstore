package com.bikkadit.electronic.store.exception;

import com.bikkadit.electronic.store.config.AppConstant;
import com.bikkadit.electronic.store.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * @param ex
     * @return
     * @Auther vaibhav
     * @ApiNote Handle Resource Not Found Exception
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExcetiom(ResourceNotFoundException ex) {
        logger.info("ResourceNotFoundException Handler Invoked !!");
        ApiResponse response = ApiResponse.builder().msg(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * @param ex
     * @return response
     * @Auther vaibhav
     * @ApiNote This api for handle MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotFoundException(MethodArgumentNotValidException ex) {
        logger.info("MethodArgumentNotValidException Handler Invoked !!");
        List<ObjectError> allError = ex.getBindingResult().getAllErrors();
        Map<String, Object> response = new HashMap<>();
        allError.stream().forEach(ObjectError -> {
                    String message = ObjectError.getDefaultMessage();
                    String field = ((FieldError) ObjectError).getField();
                    response.put(field, message);
                }


        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @param ex
     * @return status code, response
     * @Auther vaibhav
     * @apiNote api for handle bad request
     */
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponse> handleBadApiRequest(BadApiRequest ex) {
        logger.info("Bad Api Request !!");
        ApiResponse response = ApiResponse.builder().msg(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(false).build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
