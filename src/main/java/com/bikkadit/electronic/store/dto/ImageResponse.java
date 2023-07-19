package com.bikkadit.electronic.store.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ImageResponse {

    private String imageName;

    private String msg;

    private boolean success;

    private HttpStatus status;
}
