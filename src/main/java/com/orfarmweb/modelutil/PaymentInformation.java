package com.orfarmweb.modelutil;

import com.orfarmweb.entity.Orders;
import com.orfarmweb.entity.Product;
import com.orfarmweb.entity.User;
import lombok.Data;

@Data
public class PaymentInformation {
     User user;
     Product product;
     Orders order;
}
