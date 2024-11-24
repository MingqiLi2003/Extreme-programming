package com.example.demo;

import com.example.demo.domain.AddrBook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class AddrBookExcelUtil {

    // 导出数据到Excel
    public static String exportToExcel(List<AddrBook> addrBooks, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("AddrBook");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] columns = {"ID", "Name", "Age", "Type", "Sex", "Phone", "Address", "Time", "Email", "Social Media", "Label"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // 填充数据
        int rowNum = 1;
        for (AddrBook addrBook : addrBooks) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(addrBook.getId());
            row.createCell(1).setCellValue(addrBook.getName());
            row.createCell(2).setCellValue(addrBook.getAge());
            row.createCell(3).setCellValue(addrBook.getType());
            row.createCell(4).setCellValue(addrBook.getSex());
            row.createCell(5).setCellValue(addrBook.getPhone());
            row.createCell(6).setCellValue(addrBook.getAddress());
            row.createCell(7).setCellValue(System.currentTimeMillis());
            row.createCell(8).setCellValue(addrBook.getEmail());
            row.createCell(9).setCellValue(addrBook.getSocialMedia());
            row.createCell(10).setCellValue(addrBook.getAlabel());
        }

        // 写入文件
        String path=System.getProperty("user.dir")+"/src/main/resources/static";
        String fileName=System.currentTimeMillis()+".xls";
        File file=new File(path,fileName);

        try (

                OutputStream os= Files.newOutputStream(file.toPath())
//                FileOutputStream fileOut = new FileOutputStream(filePath)
        ) {

            workbook.write(os);
        }

        // 关闭工作簿
        workbook.close();
        return fileName;
    }

    // 从Excel导入数据
    public static List<AddrBook> importFromExcel(MultipartFile files) throws IOException {
        List<AddrBook> addrBooks = new ArrayList<>();
        try (FileInputStream file = (FileInputStream) files.getInputStream();
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // 跳过表头
                    continue;
                }
                AddrBook addrBook = new AddrBook();
//                addrBook.setId((int) row.getCell(0).getNumericCellValue());
                addrBook.setName(row.getCell(0).getStringCellValue());
                addrBook.setAge(row.getCell(1).getStringCellValue());
                addrBook.setType(row.getCell(2).getStringCellValue());
                addrBook.setSex(row.getCell(3).getStringCellValue());
                addrBook.setPhone((long) row.getCell(4).getNumericCellValue());
                addrBook.setAddress(row.getCell(5).getStringCellValue());
                addrBook.setTime(Long.valueOf(row.getCell(6).getStringCellValue()));
                addrBook.setEmail(row.getCell(7).getStringCellValue());
                addrBook.setSocialMedia(row.getCell(8).getStringCellValue());
                addrBook.setAlabel(String.valueOf((int)row.getCell(9).getNumericCellValue()));
                addrBooks.add(addrBook);
                System.out.println(addrBook.toString());
            }
        }
        return addrBooks;
    }

    public static void main(String[] args) {

    }
}