package com.orfarmweb.controller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.PasswordDTO;
import com.orfarmweb.service.CartService;
import com.orfarmweb.service.CategoryService;
import com.orfarmweb.service.OrderService;
import com.orfarmweb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final OrderService orderService;
    private final FormatPrice formatPrice;

    public UserController(UserService userService, CategoryService categoryService,
                          CartService cartService, OrderService orderService, FormatPrice formatPrice) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.formatPrice = formatPrice;
    }

    @ModelAttribute
    public void addAttributeToHeader(Model model) {
        model.addAttribute("listCategory", categoryService.getListCategory());
        model.addAttribute("format", formatPrice);
        model.addAttribute("countCartItem", cartService.countNumberOfItemInCart());
    }

    @GetMapping("/login")
    public String getViewLogin() {
        return "login";
    }

    @GetMapping("/createAccount")
    public String getViewCreateAccount(Model model) {
        model.addAttribute("user", new User());
        return "createAccount";
    }

    @PostMapping("/processRegister")
    public String handleCreateAccount(@ModelAttribute @Valid User user,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        if (userService.checkExist(user.getEmail()))
            bindingResult.rejectValue("email", "invalid", "Email đã tồn tại");
        if (bindingResult.hasErrors()) return "/createAccount";
        userService.registerUser(user);
        redirectAttributes.addFlashAttribute("msg", "Tạo tài khoản thành công");
        return "redirect:/login";
    }

    @GetMapping("/user/personal-information")
    public String getViewUserInformation(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("userInfo", user);
        model.addAttribute("checkpassword", new PasswordDTO());
        return "personal-infor";
    }

    @PostMapping("/user/edit-user")
    public String handleEditUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.updateUser(userService.getCurrentUser().getId(), user);
        redirectAttributes.addFlashAttribute("msg", "Cập nhật thông tin thành công");
        return "redirect:/user/personal-information";
    }

    @PostMapping("/user/edit-password")
    public String handleEditPassword(RedirectAttributes redirectAttributes, @ModelAttribute PasswordDTO passwordDTO) {
        String msg;
        if (userService.updatePassword(passwordDTO)) msg = "Thay đổi mật khẩu thành công";
        else msg = "Thay đổi mật khẩu thất bại";
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/user/personal-information";
    }

    @GetMapping("/user/order-history")
    public String getViewOrderHistory(Model model) {
        model.addAttribute("listOrder", orderService.getListOrderByCurrentUser());
        return "order-history";
    }

}
