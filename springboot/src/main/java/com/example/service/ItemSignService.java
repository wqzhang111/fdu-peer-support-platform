package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Account;
import com.example.entity.ItemSign;
import com.example.exception.CustomException;
import com.example.mapper.ItemSignMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItemSignService {

    @Resource
    ItemSignMapper itemSignMapper;

    public void add(ItemSign itemSign) {
        Account currentUser = TokenUtils.getCurrentUser();
        ItemSign as = this.selectByItemIdAndUserId(itemSign.getItemId(), currentUser.getId());  // 查看用户是否已经报名
        if (as != null) {
            throw new CustomException(ResultCodeEnum.ITEM_SIGN_ERROR);
        }
        itemSign.setUserId(currentUser.getId());
        itemSign.setTime(DateUtil.now());
        itemSignMapper.insert(itemSign);
    }

    public ItemSign selectByItemIdAndUserId(Integer actId, Integer userId) {
        return itemSignMapper.selectByItemIdAndUserId(actId, userId);
    }

    public PageInfo<ItemSign> selectPage(ItemSign itemSign, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ItemSign> list = itemSignMapper.selectAll(itemSign);
        return PageInfo.of(list);
    }

    public void deleteById(Integer id) {
        itemSignMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public void userDelete(Integer itemId, Integer useId) {
        itemSignMapper.userDelete(itemId, useId);
    }
}
