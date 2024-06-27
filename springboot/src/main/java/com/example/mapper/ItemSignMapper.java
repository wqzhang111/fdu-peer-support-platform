package com.example.mapper;

import com.example.entity.ItemSign;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemSignMapper {

    @Insert("insert into item_sign ( item_id, user_id, time ) values ( #{itemId}, #{userId}, #{time})")
    void insert(ItemSign itemSign);

    @Select("select * from item_sign where item_id = #{itemId} and user_id = #{userId}")
    ItemSign selectByItemIdAndUserId(@Param("itemId") Integer actId, @Param("userId") Integer userId);

    @Select("select item_sign.*, item.name as itemName, user.name as userName from item_sign " +
            "left join item on item_sign.item_id = item.id " +
            "left join user on item_sign.user_id = user.id " +
            "order by id desc")
    List<ItemSign> selectAll(ItemSign itemSign);

    @Delete("delete from item_sign where id = #{id}")
    void deleteById(Integer id);

    @Delete("delete from item_sign where item_id = #{itemId} and user_id = #{userId}")
    void userDelete(@Param("itemId") Integer itemId,  @Param("userId") Integer useId);
}
