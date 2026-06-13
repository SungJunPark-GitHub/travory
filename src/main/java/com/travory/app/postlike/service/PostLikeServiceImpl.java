package com.travory.app.postlike.service;

import com.travory.app.postlike.mapper.PostLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeMapper postLikeMapper;

    @Override
    public void likePost(Long postId, Long userId) {
        if (!isLikedByUser(postId, userId)) {
            postLikeMapper.insertPostLike(postId, userId);
        }
    }

    @Override
    public void unlikePost(Long postId, Long userId) {
        postLikeMapper.deletePostLike(postId, userId);
    }

    @Override
    public int getLikeCount(Long postId) {
        return postLikeMapper.countByPostId(postId);
    }

    @Override
    public boolean isLikedByUser(Long postId, Long userId) {
        return postLikeMapper.existsByPostIdAndUserId(postId, userId) > 0;
    }
}
