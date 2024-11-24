package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName(value ="addr_book")
public class AddrBook {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String age; // 通常情况下，年龄应该使用整数类型，但这里按照你的数据库定义使用字符串
    private String type;
    private String sex;
    private Long phone; // 电话号码通常不需要那么大的范围，但这里按照你的数据库定义使用 bigint
    private String address;
    private Long time; // 时间戳通常使用 Long 类型
    private String email;
    private String socialMedia;
    
    // alabel 字段使用更具体的枚举类型或布尔类型可能更合适，但这里按照你的数据库定义使用字符串
    // 如果要使用布尔类型，可以添加一个转换方法，但在这里直接使用字符串
    private String alabel; // 标记 0收藏 1书签


}