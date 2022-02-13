package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Product;
import com.orfarmweb.modelutil.FilterProduct;
import com.orfarmweb.modelutil.SearchDTO;
import com.orfarmweb.service.CartService;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final FormatPrice formatPrice;

    public ProductController(ProductService productService, CategoryService categoryService,
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

    @GetMapping("/category/{id}")
    public String redirectViewProduct(@PathVariable("id") int id) {
        return "redirect:/category/{id}/1";
    }

    @GetMapping("/category/{id}/filter-result")
    public String getViewProductFilter(@PathVariable("id") int id) {
        return "redirect:/category/{id}/filter-result/1";
    }

    @GetMapping("/category/{id}/{page}")
    public String getViewProduct(@PathVariable("id") int id,
                                 @PathVariable("page") long currentPage,
                                 Model model) {
        long totalPage = productService.getTotalPage(id);
        Integer sum = productService.getTotal(id);
        List<Product> productList = productService.getListProductByHot();
        Collections.shuffle(productList);
        if (productList.size() < 4) model.addAttribute("bestSeller", productList);
        else model.addAttribute("bestSeller", productList.subList(0, 3));
        model.addAttribute("filter", new FilterProduct());
        model.addAttribute("input", new SearchDTO());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("categoryId", id);
        model.addAttribute("sum", sum);
        model.addAttribute("listProduct", productService.getByPage(currentPage, id));
        model.addAttribute("category", categoryService.getCategoryById(id).get());
        return "raucusach";
    }

    @GetMapping("/category/{id}/fill-result/{page}")
    public String getViewProductFill(@PathVariable("id") int id,
                                     @ModelAttribute FilterProduct filter,
                                     @PathVariable("page") long currentPage,
                                     Model model) {
        Float start = filter.getFillStart();
        Float end = filter.getFillEnd();
        if (start > end) {
            Float temp = start;
            start = end;
            end = temp;
        }
        Integer sum = productService.getTotalByFill(start, end, id);
        long totalPage = productService.getTotalPageByFill(start, end, id);
        List<Product> productList = productService.getListProductByHot();
        Collections.shuffle(productList);
        if (productList.size() < 4) model.addAttribute("bestSeller", productList);
        else model.addAttribute("bestSeller", productList.subList(0, 3));
        model.addAttribute("currentFilter", filter);
        model.addAttribute("filter", new FilterProduct());
        model.addAttribute("input", new SearchDTO());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("categoryId", id);
        model.addAttribute("sum", sum);
        model.addAttribute("listProduct", productService.getListProductFillByPage(start, end, currentPage, id));
        model.addAttribute("category", categoryService.getCategoryById(id).get());
        return "dokho";
    }

    @GetMapping("/product/{id}")
    public String getViewProductDetail(@PathVariable int id, Model model) {
        Integer idCategory = productService.getCategoryId(id);
        List<Product> list = productService.getListProductByCategoryId(idCategory);
        Collections.shuffle(list);
        if (list.size() > 8) list = list.subList(0, 7);
        model.addAttribute("listSimilar", list);
        model.addAttribute("productDetail", productService.getProductById(id));
        return "productdetail";
    }

    @PostMapping("/product/{id}")
    public String handleAddProductToCart(@PathVariable("id") int id,
                                         RedirectAttributes redirectAttributes,
                                         @RequestParam("quantity") Integer quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) return "redirect:/login";
        Product product = productService.getProductById(id);
        boolean success = cartService.saveItemToCart(product, quantity);
        String msg;
        if (success) msg = "Thêm giỏ hàng thành công";
        else msg = "Thêm giỏ hàng thất bại";
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/product/{id}";
    }

    @GetMapping("/category/{id}/fillByName")
    public String getViewSearchByName() {
        return "redirect:/category/{id}/fillByName/1";
    }

    @GetMapping("/category/{id}/fillByName/{page}")
    public String handleViewSearchByName(@PathVariable("page") long currentPage,
                                         @PathVariable("id") int id, Model model,
                                         @ModelAttribute SearchDTO searchDTO) {
        long totalPage = productService.getTotalPageByName(id, searchDTO.getName());
        List<Product> productList = productService.getListProductByHot();
        Collections.shuffle(productList);
        if (productList.size() < 4) model.addAttribute("bestSeller", productList);
        else model.addAttribute("bestSeller", productList.subList(0, 3));
        model.addAttribute("input", new SearchDTO());
        model.addAttribute("category", categoryService.getCategoryById(id).get());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        List<Product> dsProduct = productService.findProductByName(id, searchDTO.getName(), currentPage);
        model.addAttribute("filter", new FilterProduct());
        model.addAttribute("categoryId", id);
        model.addAttribute("sum", dsProduct.size());
        model.addAttribute("listProduct", dsProduct);
        model.addAttribute("currentFilter", searchDTO.getName());
        return "thucphamkhac";
    }
}
