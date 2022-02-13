package com.orfarmweb.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String image;
    @OneToMany(targetEntity = Product.class, mappedBy = "category",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Product> product;
}
