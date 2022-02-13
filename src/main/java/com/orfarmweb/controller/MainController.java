package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Product;
import com.orfarmweb.service.CartService;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collections;
import java.util.List;

@Controller
public class MainController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final FormatPrice formatPrice;

    public MainController(ProductService productService, CategoryService categoryService,
                          CartService cartService, FormatPrice formatPrice) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.formatPrice = formatPrice;
    }

    @ModelAttribute
    public void addAttributeToHeader(Model model) {
        model.addAttribute("listCategory", categoryService.getListCategory());
        model.addAttribute("format", formatPrice);
        model.addAttribute("countCartItem", cartService.countNumberOfItemInCart());
    }

    @ModelAttribute
    public void addListProduct(Model model) {
        List<Product> hotproductList = productService.getListProductByHot();
        int numberOfHotProduct = 6;
        Collections.shuffle(hotproductList);
        if (hotproductList.size() < 7) model.addAttribute("listHotProduct", hotproductList);
        else model.addAttribute("listHotProduct", hotproductList.subList(0, numberOfHotProduct));
        List<Product> productList = productService.getListSaleProduct();
        int numberOfSaleProduct = 8;
        Collections.shuffle(productList);
        if (productList.size() < 9) model.addAttribute("listSaleProduct", productList);
        else model.addAttribute("listSaleProduct", productList.subList(0, numberOfSaleProduct));
    }

    @GetMapping(value = {"/", "/index", "/home"})
    public String getViewHomepage() {
        return "index";
    }
}
