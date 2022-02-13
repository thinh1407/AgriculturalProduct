package com.orfarmweb.controller.admincontroller;

import com.orfarmweb.config.OrderDataExcelExport;
import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.constaint.Status;
import com.orfarmweb.entity.Orders;
import com.orfarmweb.modelutil.DateFilterDTO;
import com.orfarmweb.modelutil.OrderAdmin;
import com.orfarmweb.service.AdminService;
import com.orfarmweb.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class OrderAdminController {
    private final AdminService adminService;
    private final FormatPrice formatPrice;
    private final OrderService orderService;

    public OrderAdminController(AdminService adminService, FormatPrice formatPrice, OrderService orderService) {
        this.adminService = adminService;
        this.formatPrice = formatPrice;
        this.orderService = orderService;
    }

    @ModelAttribute
    public void addFormatService(Model model) {
        model.addAttribute("format", formatPrice);
    }

    @GetMapping("/admin/order")
    public String getListOrderAdmin(Model model) {
        model.addAttribute("countOrder", adminService.countOrders());
        model.addAttribute("countCart", adminService.countCart());
        model.addAttribute("dateFill", new DateFilterDTO());
        model.addAttribute("orderAdmin", adminService.getOrderAdmin());
        model.addAttribute("countProcessing", adminService.countByStatus(Status.PROCESSING.getValue()));
        model.addAttribute("countCancel", adminService.countByStatus(Status.CANCELED.getValue()));
        model.addAttribute("countDelivered", adminService.countByStatus(Status.DELIVERED.getValue()));
        model.addAttribute("countApproved", adminService.countByStatus(Status.APPROVED.getValue()));
        return "admin-page/order";
    }

    @GetMapping("/admin/order/{id}")
    public String getViewOrderAdmin(@PathVariable int id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        return "admin-page/view-order";
    }

    @PostMapping("/admin/order/edit/{id}")
    public String handleEditStatusOrderAdmin(@PathVariable int id, @ModelAttribute Orders orders,
                                             @RequestParam Status status) {
        orders.setStatus(status);
        orderService.updateStatus(id, orders);
        return "redirect:/admin/order/{id}";
    }

    @GetMapping("/admin/export")
    public String exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String fileName = "order_" + currentDateTime + ".xlsx";
        String headerValue = "attachement; filename= " + fileName;
        response.setHeader(headerKey, headerValue);
        List<OrderAdmin> orderAdmins = adminService.getOrderAdmin();
        OrderDataExcelExport orderDataExcelExport = new OrderDataExcelExport(orderAdmins);
        orderDataExcelExport.export(response);
        return "admin-page/order";
    }

    @GetMapping("/admin/exportFill")
    public String exportFillToExcel(HttpServletResponse response, @ModelAttribute DateFilterDTO dateParam)
            throws IOException, IllegalStateException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String fileName = "order_" + currentDateTime + "from_" + dateParam.getStartFill() + "_to_" + dateParam.getEndFill() + ".xlsx";
        String headerValue = "attachement; filename= " + fileName;
        response.setHeader(headerKey, headerValue);
        List<OrderAdmin> orderAdmins = adminService.getListOrderAdminByFilter(dateParam.getStartFill(), dateParam.getEndFill());
        OrderDataExcelExport orderDataExcelExport = new OrderDataExcelExport(orderAdmins);
        orderDataExcelExport.export(response);
        return "admin-page/order-fill";
    }

    @PostMapping("/admin/resultFilter")
    public String handleFillByDate(@ModelAttribute DateFilterDTO dateFilterDTO, Model model,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "redirect:/admin-page/order";
        model.addAttribute("dateFill", new DateFilterDTO());
        model.addAttribute("dateParam", dateFilterDTO);
        adminService.getListOrderAdminByFilter(dateFilterDTO.getStartFill(), dateFilterDTO.getEndFill()).forEach(orderAdmin -> System.err.println(orderAdmin.toString()));
        model.addAttribute("orderAdmin", adminService.getListOrderAdminByFilter(dateFilterDTO.getStartFill(), dateFilterDTO.getEndFill()));
        return "admin-page/order-fill";
    }
//    @GetMapping("/admin/order/fill-by-status/{value}")
//    public String handleFillOrderByStatus(@PathVariable Status value, Model model){
//        model.addAttribute("orderAdmin", adminService.findOrdersByStatus(value.getValue()));
//        return "admin-page/order";
//    }
}
