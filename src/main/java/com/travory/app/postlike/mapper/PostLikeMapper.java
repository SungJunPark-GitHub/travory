package com.travory.app.postlike.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostLikeMapper {

    void insertPostLike(@Param("postId") Long postId,
                        @Param("userId") Long userId);

    void deletePostLike(@Param("postId") Long postId,
                        @Param("userId") Long userId);

    int countByPostId(Long postId);

    int existsByPostIdAndUserId(@Param("postId") Long postId,
                                @Param("userId") Long userId);
}
