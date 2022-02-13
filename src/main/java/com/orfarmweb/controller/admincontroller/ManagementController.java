package com.orfarmweb.controller.admincontroller;

import com.orfarmweb.constaint.Role;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.PasswordDTO;
import com.orfarmweb.service.AdminService;
import com.orfarmweb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ManagementController {
    private final AdminService adminService;
    private final UserService userService;

    public ManagementController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping("/admin/staffManager")
    public String getViewStaff(Model model) {
        model.addAttribute("staffList", adminService.getListUserByRole(Role.STAFF));
        return "admin-page/staff";
    }

    @GetMapping("/admin/staffManager/addStaff")
    public String getViewAddStaff(Model model) {
        model.addAttribute("staff", new User());
        return "/admin-page/add-staff";
    }

    @PostMapping("/admin/staffManager/addStaff")
    public String handleAddStaff(RedirectAttributes redirectAttributes, @ModelAttribute User user) {
        if (adminService.addStaff(user))
            redirectAttributes.addFlashAttribute("msg", "Thêm nhân viên thành công");
        return "redirect:/admin/staffManager";
    }

    @GetMapping("/admin/staffManager/editStaff/{id}")
    public String getViewEditStaff(Model model, @PathVariable("id") int id) {
        model.addAttribute("staff", adminService.getUserById(id));
        return "/admin-page/add-staff";
    }

    @PostMapping("/admin/staffManager/editStaff/{id}")
    public String handleEditStaff(RedirectAttributes redirectAttributes,
                                  @ModelAttribute User user, @PathVariable("id") int id) {
        if (adminService.updateStaff(id, user))
            redirectAttributes.addFlashAttribute("msg", "Thêm nhân viên thành công");
        return "redirect:/admin/staffManager";
    }

    @GetMapping("admin/staffManager/deleteStaff/{id}")
    public String handleDeleteStaff(@PathVariable("id") int id) {
        adminService.deleteStaff(id);
        return "redirect:/admin/staffManager";
    }

    @GetMapping("/admin/userManager")
    public String getViewCustomer(Model model) {
        model.addAttribute("customerList", adminService.getListUserByRole(Role.CUSTOMER));
        return "/admin-page/user";
    }

    @GetMapping("/admin/userManager/delete/{id}")
    public String handleDeleteUser(@PathVariable int id) {
        adminService.deleteStaff(id);
        return "redirect:/admin/userManager";
    }


    @GetMapping("/admin/personal-infor")
    public String getViewPersonalInfo(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("checkpassword", new PasswordDTO());
        return "/admin-page/personal-infor-admin";
    }

    @PostMapping("/admin/edit-user")
    public String handleEditUser(@ModelAttribute User user) {
        userService.updateUser(userService.getCurrentUser().getId(), user);
        return "redirect:/admin/personal-infor";
    }

    @PostMapping("/admin/edit-password")
    public String handleEditPassword(RedirectAttributes redirectAttributes, @ModelAttribute PasswordDTO passwordDTO) {
        String msg;
        if (userService.updatePassword(passwordDTO)) msg = "Thay đổi mật khẩu thành công";
        else msg = "Thay đổi mật khẩu thất bại";
        redirectAttributes.addAttribute("msg", msg);
        return "redirect:/admin/personal-infor";
    }
}
