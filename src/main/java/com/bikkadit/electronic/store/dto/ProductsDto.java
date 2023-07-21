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
public class ProductsDto {

    private String id;

    @NotBlank(message = "product title should not be empty..")
    @Size(min = 5, max = 500, message = "product title should be within 5 to 500 letters!")
    private String title;

    @NotBlank(message = "product description should not be empty or null!")
    @Size(min = 50, max = 1000, message = "product title should be within 50 to 1000 letters!")
    private String description;

    @ImageNameValid
    private String image;

    private Integer price;

    private Integer discount;

    private Integer quantity;

    private Boolean live;

    private Boolean stock;

    private CategoryDto category;

    private String createdBy;

    private String updatedBy;

    private Date createdAt;

    private Date updatedAt;
}
