package com.travory.app.favorite.service;

import com.travory.app.favorite.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;

    @Override
    public void favoritePost(Long postId, Long userId) {
        if (!isFavoritedByUser(postId, userId)) {
            favoriteMapper.insertFavorite(postId, userId);
        }
    }

    @Override
    public void unfavoritePost(Long postId, Long userId) {
        favoriteMapper.deleteFavorite(postId, userId);
    }

    @Override
    public int getFavoriteCount(Long postId) {
        return favoriteMapper.countByPostId(postId);
    }

    @Override
    public boolean isFavoritedByUser(Long postId, Long userId) {
        return favoriteMapper.existsByPostIdAndUserId(postId, userId) > 0;
    }

    @Override
    public List<Map<String, Object>> getFavoritePosts(Long userId) {
        return favoriteMapper.findPostsByUserId(userId);
    }
}
