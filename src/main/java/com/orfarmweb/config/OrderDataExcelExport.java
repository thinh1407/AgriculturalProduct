package com.orfarmweb.config;

import com.orfarmweb.modelutil.OrderAdmin;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderDataExcelExport{
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<OrderAdmin> list;

    public OrderDataExcelExport(List<OrderAdmin> list) {
        this.list = list;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Orders");
    }

    private void writeHeaderRow(){
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("Tên");
        cell = row.createCell(2);
        cell.setCellValue("Số Điện Thoại");
        cell = row.createCell(3);
        cell.setCellValue("Địa Chỉ");
        cell = row.createCell(4);
        cell.setCellValue("Số Lượng");
        cell = row.createCell(5);
        cell.setCellValue("Tổng tiền");
        cell = row.createCell(6);
        cell.setCellValue("Trạng thái");
    }
    private void writeDataRows(){
        int count = 1;
        for (OrderAdmin orderAdmin:list) {
            Row row = sheet.createRow(count);
            count++;
            Cell cell = row.createCell(0);
            cell.setCellValue(orderAdmin.getOrder_id());

            cell = row.createCell(1);
            cell.setCellValue(orderAdmin.getName());

            cell = row.createCell(2);
            cell.setCellValue(orderAdmin.getPhoneNumber());

            cell = row.createCell(3);
            cell.setCellValue(orderAdmin.getAddress());

            cell = row.createCell(4);
            cell.setCellValue(orderAdmin.getTotalProduct());

            cell = row.createCell(5);
            cell.setCellValue(orderAdmin.getTotalPrice());

            cell = row.createCell(6);
            cell.setCellValue(orderAdmin.getStatus().toString());
        }
    }
    public void export(HttpServletResponse response) throws IOException, IllegalStateException{
        writeHeaderRow();
        writeDataRows();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}

