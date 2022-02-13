package com.orfarmweb.service.serviceimp;

import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.entity.Product;
import com.orfarmweb.repository.OrderDetailRepo;
import com.orfarmweb.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImp implements OrderDetailService {

    private final OrderDetailRepo orderDetailRepo;

    public OrderDetailServiceImp(OrderDetailRepo orderDetailRepo) {
        this.orderDetailRepo = orderDetailRepo;
    }

    @Override
    public OrderDetail saveOrderDetail(
            Product product, Orders orders, Float price, Integer quantity) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrders(orders);
        orderDetail.setProduct(product);
        orderDetail.setPrice(price);
        if(product.getQuantityProd() <= quantity) orderDetail.setQuantity(product.getQuantityProd());
        else orderDetail.setQuantity(quantity);
        return orderDetailRepo.saveAndFlush(orderDetail);
    }
}
