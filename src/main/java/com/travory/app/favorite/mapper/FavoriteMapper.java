package com.travory.app.favorite.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FavoriteMapper {

    void insertFavorite(@Param("postId") Long postId,
                        @Param("userId") Long userId);

    void deleteFavorite(@Param("postId") Long postId,
                        @Param("userId") Long userId);

    int countByPostId(Long postId);

    int existsByPostIdAndUserId(@Param("postId") Long postId,
                                @Param("userId") Long userId);

    List<Map<String, Object>> findPostsByUserId(Long userId);
}
