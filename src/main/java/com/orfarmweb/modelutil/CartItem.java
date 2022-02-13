package com.orfarmweb.modelutil;

import com.orfarmweb.entity.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    String image;
    Integer productId;
    String productName;
    Integer quantity;
    Float discount;
    Float salePrice;
    Float totalPrice;
    public CartItem(Product product, int quantity){
        this.image=product.getImage();
        this.productId=product.getId();
        this.productName=product.getName();
        this.discount=product.getPercentDiscount();
        this.salePrice= (product.getSalePrice() * (100 - product.getPercentDiscount())/100);
        this.totalPrice = (product.getSalePrice() * (100 - product.getPercentDiscount())/100) * quantity;
        this.quantity = quantity;
    }

}
