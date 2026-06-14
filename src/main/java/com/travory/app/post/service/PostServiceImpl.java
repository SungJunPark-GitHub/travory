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

    private static final List<String> POST_STATUSES =
            List.of("OPEN", "CLOSED", "FINISHED");

    private final PostMapper postMapper;

    @Override
    public void createPost(PostDto postDto) {
        postMapper.insertPost(postDto);
    }

    @Override
    public List<Map<String, Object>> getPostList(String keyword, int page, int size) {
        int offset = (page - 1) * size;
        return postMapper.findPostsPaged(keyword, offset, size);
    }

    @Override
    public int countPosts(String keyword) {
        return postMapper.countPosts(keyword);
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
    public void updatePostStatus(Long id, Long userId, String status) {
        if (!POST_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid post status.");
        }

        postMapper.updatePostStatus(id, userId, status);
    }

    @Override
    public void deletePost(Long id) {
        postMapper.deletePost(id);
    }
}
