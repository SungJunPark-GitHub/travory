package com.travory.app.postlike.service;

public interface PostLikeService {

    void likePost(Long postId, Long userId);

    void unlikePost(Long postId, Long userId);

    int getLikeCount(Long postId);

    boolean isLikedByUser(Long postId, Long userId);
}
