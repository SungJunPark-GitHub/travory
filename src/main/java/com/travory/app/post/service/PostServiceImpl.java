package com.travory.app.post.service;

import com.travory.app.post.dto.PostDto;
import com.travory.app.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;

    @Override
    public void createPost(PostDto postDto) {
        postMapper.insertPost(postDto);
    }

    @Override
    public List<Map<String, Object>> getPostList() {
        return postMapper.findAllWithUser();
    }

    @Override
    public PostDto getPostDetail(Long id) {
        postMapper.increaseViewCount(id);
        return postMapper.findById(id);
    }

    @Override
    public PostDto getPostById(Long id) {
        return postMapper.findById(id);
    }

    @Override
    public void updatePost(PostDto postDto) {
        postMapper.updatePost(postDto);
    }

    @Override
    public void deletePost(Long id) {
        postMapper.deletePost(id);
    }
}
