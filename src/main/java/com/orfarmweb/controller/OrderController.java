package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.*;
import com.orfarmweb.modelutil.CartItem;
import com.orfarmweb.modelutil.PaymentInformation;
import com.orfarmweb.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class OrderController {
    private final CategoryService categoryService;
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final FormatPrice formatPrice;

    public OrderController(CategoryService categoryService, CartService cartService, ProductService productService, UserService userService, OrderService orderService, OrderDetailService orderDetailService, FormatPrice format) {
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.formatPrice = format;
    }

    @ModelAttribute
    public void addAttributeToHeader(Model model) {
        model.addAttribute("listCategory", categoryService.getListCategory());
        model.addAttribute("format", formatPrice);
        model.addAttribute("countCartItem", cartService.countNumberOfItemInCart());
    }

    @GetMapping("/payment")
    public String getViewPayment(Model model, RedirectAttributes redirectAttributes) {
        List<Cart> listCart = cartService.getAllCartByUser();
        if (listCart.isEmpty()) {
            redirectAttributes.addFlashAttribute("msg", "Không có sản phẩm nào để thanh toán");
            return "redirect:/cart";
        }
        List<CartItem> listProductInCart = productService.getProductFromCart(listCart);
        Float tempPrice = productService.getTempPriceOfCart(listProductInCart);
        Float ship = 20000f;
        if (tempPrice > 50000) ship = 0f;
        Float totalPrice = tempPrice + ship;
        model.addAttribute("tempPrice", formatPrice.formatPrice(tempPrice));
        model.addAttribute("ship", formatPrice.formatPrice(ship));
        model.addAttribute("totalPrice", formatPrice.formatPrice(totalPrice));
        model.addAttribute("productInCart", listProductInCart);
        model.addAttribute("userInformation", userService.getCurrentUser());
        model.addAttribute("paymentInformation", new PaymentInformation());
        return "payment";
    }

    @PostMapping("/payment/process")
    public String handlePaymentProcess(@ModelAttribute PaymentInformation paymentInformation) {
        User user = userService.getCurrentUser();
        List<Cart> listCart = cartService.getAllCartByUser();
        List<CartItem> listProductInCart = productService.getProductFromCart(listCart);
        Float tempPrice = productService.getTempPriceOfCart(listProductInCart);
        Float ship = 20000f;
        if (tempPrice > 50000) ship = 0f;
        Float totalPrice = tempPrice + ship;
        Orders orders = orderService.saveNewOrder(paymentInformation);
        orders.setUser(user);
        Set<OrderDetail> orderDetailList = new HashSet<>();
        for (CartItem cart : listProductInCart) {
            Product product = productService.getProductById(cart.getProductId());
            OrderDetail orderDetail = orderDetailService.saveOrderDetail(
                    product, orders,
                    cart.getTotalPrice(), cart.getQuantity());
            productService.saveAfterOrder(product, orderDetail);
            orderDetailList.add(orderDetail);
        }
        orderService.saveOrder(orders, totalPrice, paymentInformation.getOrder().getNote(), orderDetailList);
        cartService.deleteAllItemInCart();
        return "redirect:/payment/ordersucess";
    }

    @GetMapping("/payment/ordersucess")
    public String getViewOrderSucess() {
        return "success-order";
    }

}
