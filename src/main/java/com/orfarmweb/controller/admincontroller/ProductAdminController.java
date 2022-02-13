package com.orfarmweb.controller.admincontroller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Product;
import com.orfarmweb.service.AdminService;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class ProductAdminController {
    private static final String currentDirectory = System.getProperty("user.dir");
    private static final Path path = Paths.get(currentDirectory + Paths.get("/target/classes/static/image/ImageOrFarm"));
    private final CategoryService categoryService;
    private final AdminService adminService;
    private final FormatPrice formatPrice;
    private final ProductService productService;

    public ProductAdminController(AdminService adminService, CategoryService categoryService,
                                  FormatPrice formatPrice, ProductService productService) {
        this.adminService = adminService;
        this.categoryService = categoryService;
        this.formatPrice = formatPrice;
        this.productService = productService;
    }

    @ModelAttribute
    public void addFormatService(Model model) {
        model.addAttribute("format", formatPrice);
    }

    @GetMapping("/admin/product")
    public String getViewProductAdmin(Model model) {
        model.addAttribute("dsProduct", adminService.getListProduct());
        return "admin-page/product";
    }

    @GetMapping("/admin/product/add")
    public String getViewAddProductAdmin(Model model) {
        model.addAttribute("categoryList", categoryService.getListCategory());
        model.addAttribute("product", new Product());
        return "admin-page/add-product";
    }

    @PostMapping("/admin/product/add")
    public String handleAddProduct(@ModelAttribute @Valid Product product,
                                   @RequestParam MultipartFile photo,
                                   BindingResult result) {
        if (photo.isEmpty() || result.hasErrors()) return "redirect:/admin/product/add";
        try {
            InputStream inputStream = photo.getInputStream();
            Files.copy(inputStream, path.resolve(photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            product.setImage(photo.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        productService.addProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/edit/{id}")
    public String getViewEditProductAdmin(@PathVariable("id") int productId, Model model) {
        model.addAttribute("categoryList", categoryService.getListCategory());
        model.addAttribute("product", productService.getProductById(productId));
        return "admin-page/add-product";
    }

    @PostMapping("/admin/product/edit/{id}")
    public String handleEditProductAdmin(@PathVariable("id") int productId, @ModelAttribute Product product, @RequestParam MultipartFile photo) {
        if (!photo.isEmpty()) {
            try {
                InputStream inputStream = photo.getInputStream();
                Files.copy(inputStream, path.resolve(photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                product.setImage(photo.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productService.updateProduct(productId, product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String handleDeleteProductAdmin(@PathVariable("id") int productId) {
        productService.deleteProduct(productId);
        return "redirect:/admin/product";
    }
}
