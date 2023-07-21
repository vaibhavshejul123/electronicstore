package com.bikkadit.electronic.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;

    @Column(name = "products_title")
    private String title;

    @Column(name = "prod_desc")
    private String description;

    @Column(name = "prod_image")
    private String image;

    @Column(name = "prod_price")
    private Integer price;

    @Column(name = "prod_discount")
    private Integer discount;

    @Column(name = "prod_quantity")
    private Integer quantity;

    private Boolean live;

    private Boolean stock;

    @ManyToOne
    @JoinColumn(name = "fk_category_id")
    private Category category;

}
