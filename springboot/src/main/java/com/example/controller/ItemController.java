package com.example.controller;

import com.example.common.Result;
import com.example.entity.Item;
import com.example.service.ItemService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 活动前端操作接口
 **/
@RestController
@RequestMapping("/item")
public class ItemController {

    @Resource
    private ItemService itemService;
    @Autowired
    private ItemController item;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Item item) {
        itemService.add(item);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        itemService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        itemService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Item item) {
        itemService.updateById(item);
        return Result.success();
    }

    @PutMapping("/updateReadCount/{itemId}")
    public Result updateReadCount(@PathVariable Integer itemId) {
        itemService.updateReadCount(itemId);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Item item = itemService.selectById(id);
        return Result.success(item);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Item item) {
        List<Item> list = itemService.selectAll(item);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Item item,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Item> page = itemService.selectPage(item, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询用户报名的活动列表
     */
    @GetMapping("/selectUser")
    public Result selectUser(Item item,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Item> page = itemService.selectUser(item, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询用户点赞的活动列表
     */
    @GetMapping("/selectLike")
    public Result selectLike(Item item,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Item> page = itemService.selectLike(item, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询用户收藏的活动列表
     */
    @GetMapping("/selectCollect")
    public Result selectCollect(Item item,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Item> page = itemService.selectCollect(item, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询用户评论的活动列表
     */
    @GetMapping("/selectComment")
    public Result selectComment(Item item,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Item> page = itemService.selectComment(item, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 热门活动
     */
    @GetMapping("/selectTop")
    public Result selectTop() {
        List<Item> list = itemService.selectTop();
        return Result.success(list);
    }

}