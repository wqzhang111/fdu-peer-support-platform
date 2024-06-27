package com.example.mapper;

import com.example.entity.Item;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 操作item相关数据接口
 */
public interface ItemMapper {

    /**
     * 新增
     */
    int insert(Item item);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Item item);

    /**
     * 根据ID查询
     */
    Item selectById(Integer id);

    /**
     * 查询所有
     */
    List<Item> selectAll(Item item);

    @Update("update item set read_count = read_count + 1 where id = #{itemId}")
    void updateReadCount(Integer itemId);

    List<Item> selectUser(Item item);

    List<Item> selectLike(Item item);

    List<Item> selectCollect(Item item);

    List<Item> selectComment(Item item);
}