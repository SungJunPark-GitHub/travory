package com.travory.app.post.mapper;

import com.travory.app.post.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    void insertPost(PostDto postDto);

    List<PostDto> findAll();

    PostDto findById(Long id);

    void increaseViewCount(Long id);
}