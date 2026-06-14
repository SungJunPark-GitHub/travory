package com.travory.app.post.service;

import com.travory.app.post.dto.PostDto;
import com.travory.app.user.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface PostService {

    void createPost(PostDto postDto);

    List<Map<String, Object>> getPostList(String keyword, String sort, int page, int size);

    int countPosts(String keyword);

    String getSortType(String sort);

    PostDto getPostDetail(Long id);

    PostDto getPostById(Long id);

    UserDto getPostAuthor(Long postId);

    void updatePost(PostDto postDto);

    void updatePostStatus(Long id, Long userId, String status);

    void deletePost(Long id);
}
