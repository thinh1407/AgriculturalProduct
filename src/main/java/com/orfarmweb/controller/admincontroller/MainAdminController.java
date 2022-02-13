package com.orfarmweb.controller.admincontroller;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.modelutil.ChartDTO;
import com.orfarmweb.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainAdminController {
    private final AdminService adminService;
    private final FormatPrice formatPrice;

    public MainAdminController(AdminService adminService, FormatPrice formatPrice) {
        this.adminService = adminService;
        this.formatPrice = formatPrice;
    }

    @ModelAttribute
    public void getTopOrder(Model model) {
        model.addAttribute("topOder", adminService.getTopOrderDetail());
        model.addAttribute("format", formatPrice);
        model.addAttribute("countUser", adminService.countCustomer());
        model.addAttribute("getRevenue", adminService.getRevenue());
        model.addAttribute("countOrder", adminService.countOrders());
        model.addAttribute("getCostOfProduct", adminService.getCostOfProduct());
    }

    @GetMapping("/admin")
    public String getViewMainAdmin(Model model) {
        model.addAttribute("dsProduct", adminService.getListProduct());
        return "admin-page/admin";
    }

    @GetMapping("/admin-2")
    public String getViewStatisticAdmin(Model model) {
        model.addAttribute("dsProduct", adminService.getListProduct());
        return "admin-page/admin2";
    }

    @PostMapping("/get-chart-information")
    @ResponseBody
    public ChartDTO handleChartInformation() {
        return adminService.getInformationForChart();
    }
}
