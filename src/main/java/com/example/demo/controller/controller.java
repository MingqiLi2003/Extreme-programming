package com.example.demo.controller;


import com.example.demo.AddrBookExcelUtil;
import com.example.demo.R;
import com.example.demo.domain.AddrBook;
import com.example.demo.service.AddrBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class controller {

    @Autowired
    private AddrBookService addrBookService;


    @GetMapping
    public R getList(){
        List<AddrBook> list = addrBookService.list();
        return R.ok(list);
    }

    @PostMapping
    public R add(@RequestBody AddrBook addrBook){
        boolean save = addrBookService.saveOrUpdate(addrBook);
        return R.ok(save);
    }

    @PostMapping("{id}")
    public R delete(@PathVariable(value = "id",name = "id") Integer id){
        boolean b = addrBookService.removeById(id);
        return R.ok(b);
    }


    @PostMapping("/excel")
    public R importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return R.fail("文件不能为空");
        }
        List<AddrBook> addrBooks = AddrBookExcelUtil.importFromExcel(file);
        System.out.println(addrBooks.toString());
        addrBookService.saveBatch(addrBooks);
        return  R.ok("导入成功!");
    }

    @PostMapping("/exportToExcel")
    public R importExcel() throws IOException {
        List<AddrBook> list = addrBookService.list();
        String path=System.getProperty("user.dir")+"/xls";
        String fileName=System.currentTimeMillis()+".xls";
        String s = AddrBookExcelUtil.exportToExcel(list, path);
        return  R.ok(s,"导出成功!");
    }






}
