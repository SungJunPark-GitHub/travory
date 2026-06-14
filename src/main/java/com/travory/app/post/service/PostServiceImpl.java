package com.travory.app.post.service;

import com.travory.app.global.exception.BadRequestException;
import com.travory.app.global.exception.NotFoundException;
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
    private static final List<String> SORT_TYPES =
            List.of("latest", "view", "like");

    private final PostMapper postMapper;

    @Override
    public void createPost(PostDto postDto) {
        postMapper.insertPost(postDto);
    }

    @Override
    public List<Map<String, Object>> getPostList(String keyword, String sort, int page, int size) {
        int offset = (page - 1) * size;
        return postMapper.findPostsPaged(keyword, getSortType(sort), offset, size);
    }

    @Override
    public int countPosts(String keyword) {
        return postMapper.countPosts(keyword);
    }

    @Override
    public String getSortType(String sort) {
        if (SORT_TYPES.contains(sort)) {
            return sort;
        }

        return "latest";
    }

    @Override
    public PostDto getPostDetail(Long id) {
        postMapper.increaseViewCount(id);
        PostDto post =
                postMapper.findById(id);

        if (post == null) {
            throw new NotFoundException("게시글을 찾을 수 없습니다.");
        }

        return post;
    }

    @Override
    public PostDto getPostById(Long id) {
        PostDto post =
                postMapper.findById(id);

        if (post == null) {
            throw new NotFoundException("게시글을 찾을 수 없습니다.");
        }

        return post;
    }

    @Override
    public void updatePost(PostDto postDto) {
        postMapper.updatePost(postDto);
    }

    @Override
    public void updatePostStatus(Long id, Long userId, String status) {
        if (!POST_STATUSES.contains(status)) {
            throw new BadRequestException("유효하지 않은 모집 상태입니다.");
        }

        postMapper.updatePostStatus(id, userId, status);
    }

    @Override
    public void deletePost(Long id) {
        postMapper.deletePost(id);
    }
}
