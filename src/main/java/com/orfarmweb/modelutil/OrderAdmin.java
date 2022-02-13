package com.orfarmweb.modelutil;
import com.orfarmweb.constaint.Status;
import com.orfarmweb.entity.Orders;
import lombok.Data;

@Data
public class OrderAdmin {
    private Integer order_id;
    private String name;
    private Float totalPrice;
    private Status status;
    private Integer totalProduct;
    private String address;
    private String phoneNumber;
    private Float salePrice;

    public OrderAdmin(Orders orders, Integer totalProduct) {
        this.order_id=orders.getId();
        this.name = orders.getUser().getFirstName() + " " + orders.getUser().getLastName();
        this.totalPrice = orders.getTotalPrice();
        this.status = orders.getStatus();
        this.phoneNumber = orders.getPhoneNumber();
        this.address = orders.getAddress();
        this.salePrice = orders.getTotalPrice();
        this.totalProduct = totalProduct;
    }
}
