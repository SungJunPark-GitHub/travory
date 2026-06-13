package com.travory.app.favorite.service;

import java.util.List;
import java.util.Map;

public interface FavoriteService {

    void favoritePost(Long postId, Long userId);

    void unfavoritePost(Long postId, Long userId);

    int getFavoriteCount(Long postId);

    boolean isFavoritedByUser(Long postId, Long userId);

    List<Map<String, Object>> getFavoritePosts(Long userId);
}
