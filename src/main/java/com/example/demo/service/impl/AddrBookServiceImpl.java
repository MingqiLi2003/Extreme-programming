package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.AddrBook;
import com.example.demo.service.AddrBookService;
import com.example.demo.mapper.AddrBookMapper;
import org.springframework.stereotype.Service;

/**
* @author bin
* @description 针对表【addr_book】的数据库操作Service实现
* @createDate 2024-11-23 12:58:22
*/
@Service
public class AddrBookServiceImpl extends ServiceImpl<AddrBookMapper, AddrBook>
    implements AddrBookService{

}




