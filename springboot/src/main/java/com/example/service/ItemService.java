package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.LikesModuleEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.mapper.ItemMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动业务处理
 **/
@Service
public class ItemService {

    @Resource
    private ItemMapper itemMapper;

    @Resource
    ItemSignService itemSignService;

    @Resource
    LikesService likesService;

    @Resource
    CollectService collectService;

    /**
     * 新增
     */
    public void add(Item item) {
        itemMapper.insert(item);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        itemMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            itemMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Item item) {
        itemMapper.updateById(item);
    }

    /**
     * 根据ID查询
     */
    public Item selectById(Integer id) {
        Item item = itemMapper.selectById(id);
        this.setAct(item, TokenUtils.getCurrentUser());

        int likesCount = likesService.selectByFidAndModule(id, LikesModuleEnum.ITEM.getValue());
        int collectCount = collectService.selectByFidAndModule(id, LikesModuleEnum.ITEM.getValue());
        item.setLikesCount(likesCount);
        item.setCollectCount(collectCount);

        Likes likes = likesService.selectUserLikes(id, LikesModuleEnum.ITEM.getValue());
        item.setIsLike(likes != null);

        Collect collect = collectService.selectUserCollect(id, LikesModuleEnum.ITEM.getValue());
        item.setIsCollect(collect != null);
        return item;
    }

    /**
     * 查询所有
     */
    public List<Item> selectAll(Item item) {
        return itemMapper.selectAll(item);
    }

    /**
     * 分页查询
     */
    public PageInfo<Item> selectPage(Item item, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Item> list = itemMapper.selectAll(item);
        PageInfo<Item> pageInfo = PageInfo.of(list);
        List<Item> itemList = pageInfo.getList();
        Account currentUser = TokenUtils.getCurrentUser();
        for (Item act : itemList) {
            this.setAct(act, currentUser);
        }
        return pageInfo;
    }

    // 设置活动额外信息
    private void setAct(Item act, Account currentUser) {
        act.setIsEnd(DateUtil.parseDate(act.getTime()).isBefore(new Date()));  // 活动的结束时间在当前时间之前  就表示活动结束了
        ItemSign itemSign = itemSignService.selectByItemIdAndUserId(act.getId(), currentUser.getId());
        act.setIsSign(itemSign != null);
    }

    /**
     * 热门活动
     */
    public List<Item> selectTop() {
        List<Item> itemList = this.selectAll(null);
        itemList = itemList.stream().sorted((b1, b2) -> b2.getReadCount().compareTo(b1.getReadCount()))
                .limit(2)
                .collect(Collectors.toList());
        return itemList;
    }

    public void updateReadCount(Integer itemId) {
        itemMapper.updateReadCount(itemId);
    }

    // 查询出用户报名的活动列表
    public PageInfo<Item> selectUser(Item item, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            item.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Item> list = itemMapper.selectUser(item);
        PageInfo<Item> pageInfo = PageInfo.of(list);
        List<Item> itemList = pageInfo.getList();
        for (Item act : itemList) {
            this.setAct(act, currentUser);
        }
        return pageInfo;
    }

    public PageInfo<Item> selectLike(Item item, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            item.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Item> list = itemMapper.selectLike(item);
        PageInfo<Item> pageInfo = PageInfo.of(list);
        List<Item> itemList = pageInfo.getList();
        for (Item act : itemList) {
            this.setAct(act, currentUser);
        }
        return pageInfo;
    }

    public PageInfo<Item> selectCollect(Item item, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            item.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Item> list = itemMapper.selectCollect(item);
        PageInfo<Item> pageInfo = PageInfo.of(list);
        List<Item> itemList = pageInfo.getList();
        for (Item act : itemList) {
            this.setAct(act, currentUser);
        }
        return pageInfo;
    }

    public PageInfo<Item> selectComment(Item item, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            item.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Item> list = itemMapper.selectComment(item);
        PageInfo<Item> pageInfo = PageInfo.of(list);
        List<Item> itemList = pageInfo.getList();
        for (Item act : itemList) {
            this.setAct(act, currentUser);
        }
        return pageInfo;
    }}

