package com.example.demo.mapper;

import com.example.demo.domain.AddrBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author bin
* @description 针对表【addr_book】的数据库操作Mapper
* @createDate 2024-11-23 12:58:22
* @Entity com.example.demo.domain.AddrBook
*/
@Mapper
public interface AddrBookMapper extends BaseMapper<AddrBook> {

}




