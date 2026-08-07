package com.cupk.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_book")
public class Book {
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    String name;
    Double price;
    String category;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date time;
    String isbn;
    String author;
    String press;
}
