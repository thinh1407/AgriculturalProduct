package com.orfarmweb.controller;

import com.orfarmweb.service.CartService;
import com.orfarmweb.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
@Controller
public class ShowViewController {
    private final CategoryService categoryService;
    private final CartService cartService;

    public ShowViewController(CategoryService categoryService, CartService cartService) {
        this.categoryService = categoryService;
        this.cartService = cartService;
    }

    @ModelAttribute
    public void addAttributeToHeader(Model model){
        model.addAttribute("listCategory", categoryService.getListCategory());
        model.addAttribute("countCartItem", cartService.countNumberOfItemInCart());
    }

    @GetMapping("/blog")
    public String getViewBlog(){
        return "blog";
    }
    @GetMapping("/information")
    public String getViewInfo(){
        return "information";
    }
    @GetMapping("/license")
    public String getViewLicense(){
        return "license";
    }
    @GetMapping("/policy")
    public String getViewPolicy(){
        return "policy";
    }
    @GetMapping("/condition")
    public String getViewCondition(){
        return "condition";
    }
    @GetMapping("/contact")
    public String getViewContact(){
        return "contact";
    }

}
