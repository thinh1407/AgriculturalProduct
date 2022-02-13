package com.orfarmweb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(targetEntity = Product.class)
    private Product product;
    private Integer quantity;
    private boolean isDelete=false;
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.MERGE)
    private User user;
    public Cart(OrderDetail orderDetail){
        this.product = orderDetail.getProduct();
        this.quantity = orderDetail.getQuantity();
        this.user = orderDetail.getOrders().getUser();
    }
}
