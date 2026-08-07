package com.cupk.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cupk.pojo.Book;

@Mapper
public interface BookMapper extends BaseMapper<Book> {

}
