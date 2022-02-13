package com.orfarmweb.service;

import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.entity.Product;

import java.util.List;

public interface CartService {
    boolean saveItemToCart(Product product, Integer quantity);
    List<Cart> getAllCartByUser();
    Integer countNumberOfItemInCart();
    boolean deleteAllItemInCart();
    void saveNewQuantity(List<Cart> cartList, List<Integer> soluong);
    boolean deleteAnItemInCart(int productId);
    void saveItemToCartByOrder(Orders orders);
}
