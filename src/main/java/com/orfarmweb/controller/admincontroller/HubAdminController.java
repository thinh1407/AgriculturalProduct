package com.orfarmweb.controller.admincontroller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.modelutil.ProductAdminDTO;
import com.orfarmweb.modelutil.SearchDTO;
import com.orfarmweb.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller

public class HubAdminController {
    private final AdminService adminService;
    private final FormatPrice formatPrice;

    public HubAdminController(AdminService adminService, FormatPrice formatPrice) {
        this.adminService = adminService;
        this.formatPrice = formatPrice;
    }

    @ModelAttribute
    public void addFormatService(Model model) {
        model.addAttribute("format", formatPrice);
    }

    @GetMapping("/admin/hub")
    public String getViewHub(Model model) {
        List<ProductAdminDTO> dsProduct = adminService.getHub();
        model.addAttribute("input", new SearchDTO());
        model.addAttribute("dsProduct", dsProduct);
        return "admin-page/hub";
    }
//    @GetMapping("/admin/hub/fillByName")
//    public String showViewSearchByName(){
//        return "redirect:/admin/hub/fillByName/1";
//    }
//    @GetMapping("/admin/hub/fillByName/{page}")
//    public String handleViewSearchByName(@PathVariable("page") long currentPage, Model model, @ModelAttribute SearchDTO searchDTO){
//        long totalPage = adminService.getTotalPageHubByKeyWord(searchDTO.getName());
//        model.addAttribute("input", new SearchDTO());
//        model.addAttribute("totalPage", totalPage);
//        model.addAttribute("currentPage", currentPage);
//        List<ProductAdminDTO> dsProduct = adminService.searchHubByNameAndPage(searchDTO.getName(),currentPage);
//        model.addAttribute("dsProduct",dsProduct);
//        model.addAttribute("currentFilter", searchDTO);
//        return "admin-page/hub-name";
//    }
}
