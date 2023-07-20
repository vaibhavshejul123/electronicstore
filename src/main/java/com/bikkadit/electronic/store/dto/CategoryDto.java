package com.bikkadit.electronic.store.dto;

import com.bikkadit.electronic.store.util.ImageNameValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank(message = "category title must not be empty or null!")
    @Size(min = 7, max = 45, message = "category title must be in the range of 7 to 45 characters!")
    private String title;

    @NotBlank(message = "category title must not be empty or null!")
    @Size(min = 5, max = 50, message = "category title must be in the range of 5 to 50 characters!")
    private String description;

    @ImageNameValid
    private String coverImage;

    private String createdBy;

    private String updatedBy;

    private Date createdAt;

    private Date updatedAt;
}
