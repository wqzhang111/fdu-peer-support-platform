package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.mapper.PostMapper;
import com.example.utils.TokenUtils;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PostService {

    @Resource
    private PostMapper postMapper;

    @Resource
    UserService userService;



    /**
     * add
     */
    public void add(Post post) {
        post.setDate(DateUtil.today());
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            post.setUserId(currentUser.getId());
        }
        postMapper.insert(post);
    }

    /**
     * delete
     */
    public void deleteById(Integer id) {
        postMapper.deleteById(id);
    }


    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            postMapper.deleteById(id);
        }
    }

    /**
     * update
     */
    public void updateById(Post post) {
        postMapper.updateById(post);
    }

    /**
     * search by id
     */
    public Post selectById(Integer id) {
        Post post = postMapper.selectById(id);
        User user = userService.selectById(post.getUserId());
        List<Post> userPostList = postMapper.selectUserPost(user.getId());
        user.setPostCount(userPostList.size());
        return post;
    }

    /**
     * search all
     */
    public List<Post> selectAll(Post post) {
        return postMapper.selectAll(post);
    }


}
