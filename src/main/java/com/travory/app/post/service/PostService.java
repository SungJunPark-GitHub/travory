package com.travory.app.post.service;

import com.travory.app.post.dto.PostDto;

import java.util.List;
import java.util.Map;

public interface PostService {

    void createPost(PostDto postDto);

    List<Map<String, Object>> getPostList();

    PostDto getPostDetail(Long id);
}