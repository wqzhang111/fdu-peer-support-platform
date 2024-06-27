package com.example.controller;

import com.example.common.Result;
import com.example.entity.ItemSign;
import com.example.service.ItemSignService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/itemSign")
public class ItemSignController {

    @Resource
    ItemSignService itemSignService;

    @PostMapping("/add")
    public Result add(@RequestBody ItemSign itemSign) {
        itemSignService.add(itemSign);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        itemSignService.deleteById(id);
        return Result.success();
    }

    /**
     * 用户删除
     */
    @DeleteMapping("/delete/user/{itemId}/{useId}")
    public Result userDelete(@PathVariable Integer itemId, @PathVariable Integer useId) {
        itemSignService.userDelete(itemId, useId);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        itemSignService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(ItemSign itemSign,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<ItemSign> page = itemSignService.selectPage(itemSign, pageNum, pageSize);
        return Result.success(page);
    }

}
