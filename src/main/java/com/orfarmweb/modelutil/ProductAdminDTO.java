package com.orfarmweb.modelutil;

import com.orfarmweb.entity.Product;
import lombok.Data;

@Data
public class ProductAdminDTO {
    private Integer id;
    private String name;
    private Float salePrice;
    private Integer quantityProd;
    private Float cost;
    private String cate_name;

    public ProductAdminDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.salePrice = product.getSalePrice();
        this.quantityProd = product.getQuantityProd();
        this.cost = product.getCost();
        this.cate_name = product.getCategory().getName();
    }
}
