package com.travory.app.post.service;

import com.travory.app.post.dto.PostDto;

import java.util.List;
import java.util.Map;

public interface PostService {

    void createPost(PostDto postDto);

    List<Map<String, Object>> getPostList(String keyword, int page, int size);

    int countPosts(String keyword);

    PostDto getPostDetail(Long id);

    PostDto getPostById(Long id);

    void updatePost(PostDto postDto);

    void updatePostStatus(Long id, Long userId, String status);

    void deletePost(Long id);
}
