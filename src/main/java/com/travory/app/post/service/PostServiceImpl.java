package com.travory.app.post.service;

import com.travory.app.post.dto.PostDto;
import com.travory.app.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;

    @Override
    public void createPost(PostDto postDto) {
        postMapper.insertPost(postDto);
    }

    @Override
    public List<PostDto> getPostList() {
        return postMapper.findAll();
    }

    @Override
    public PostDto getPostDetail(Long id) {
        postMapper.increaseViewCount(id);
        return postMapper.findById(id);
    }
}