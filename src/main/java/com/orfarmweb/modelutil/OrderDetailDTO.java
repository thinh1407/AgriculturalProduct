package com.orfarmweb.modelutil;

import com.orfarmweb.constaint.Status;
import com.orfarmweb.entity.OrderDetail;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class OrderDetailDTO {
    private String name;
    private int order_id;
    private Date date;
    private Integer quantity;
    private Status status;

    public OrderDetailDTO(OrderDetail orderDetail) {
        this.name = orderDetail.getProduct().getName();
        this.order_id=orderDetail.getOrders().getId();
        this.date = orderDetail.getOrders().getCreateAt();
        this.quantity = orderDetail.getQuantity();
        this.status = orderDetail.getOrders().getStatus();
    }
}
