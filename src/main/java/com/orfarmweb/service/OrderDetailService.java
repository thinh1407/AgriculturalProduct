package com.orfarmweb.service;

import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.entity.Product;

public interface OrderDetailService {
    OrderDetail saveOrderDetail(Product product, Orders orders, Float price, Integer quantity);
}
