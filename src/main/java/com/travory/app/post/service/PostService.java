package com.travory.app.post.service;

import com.travory.app.post.dto.PostDto;

import java.util.List;

public interface PostService {

    void createPost(PostDto postDto);

    List<PostDto> getPostList();

    PostDto getPostDetail(Long id);
}