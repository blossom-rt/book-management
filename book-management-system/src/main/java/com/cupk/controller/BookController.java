package com.cupk.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cupk.mapper.BookMapper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cupk.common.Result;
import com.cupk.pojo.Book;


@RestController
@RequestMapping("/book")
public class BookController {
    private final BookMapper bookMapper;

    BookController(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }
    
    // // 查询全部数据 
    // @GetMapping("/books")
    // public Result selectAll() {
    //     return Result.success(bookMapper.selectList(null));
    // }

    // 查询全部数据-分页
    @GetMapping("/books")
    public Result selectPages(@RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize){
        Page<Book> page = new Page<>(pageNum, pageSize);
        // bookMapper.selectPage(page, null);
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id"); // 按id倒序，新数据在前
        bookMapper.selectPage(page, queryWrapper);

        System.out.println("当前的页数：" + page.getCurrent());
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("总页数：" + page.getPages());
        System.out.println("是否有下一页：" + page.hasNext());
        return Result.success(page);
    }

    // 根据id查询数据
    @GetMapping("/books/{id}")
    public Result selectById(@PathVariable Integer id) {
        Book book = bookMapper.selectById(id);
        if(book != null) {
            System.out.println("数据查询成功！");
            return Result.success(book);
        } else {
            System.out.println("数据查询失败！");
            return Result.error();
        }
    }

    // 添加数据
    @PostMapping("/books")
    public Result insertBook(@RequestBody Book book) {
        int i = bookMapper.insert(book);
        if(i > 0) {
            System.out.println("数据添加成功");
            return Result.success();
        } else {
            System.out.println("数据添加失败");
            return Result.error();
        }
    }

    // 修改数据
    @PutMapping("/books")
    public Result updateBook(@RequestBody Book book) {
        int i = bookMapper.updateById(book);
        if(i > 0) {
            System.out.println("数据修改成功");
            return Result.success();
        } else {
            System.out.println("数据修改失败");
            return Result.error();
        }
    }

    // 批量删除数据
    @DeleteMapping("/books/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.error("400", "请选择要删除的数据");
        }
        for (Integer id : ids) {
            bookMapper.deleteById(id);
        }
        System.out.println("批量删除成功，共删除" + ids.size() + "条");
        return Result.success();
    }

    // 根据id删除数据
    @DeleteMapping("/books/{id}")
    public Result deleteById(@PathVariable Integer id) {
        int i = bookMapper.deleteById(id);
        if(i > 0) {
            System.out.println("数据删除成功");
            return Result.success();
        } else {
            System.out.println("数据删除失败");
            return Result.error();
        }
    }
}
