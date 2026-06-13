package com.travory.app.companion.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CompanionMapper {

    void insertRequest(@Param("postId") Long postId,
                       @Param("userId") Long userId,
                       @Param("message") String message);

    List<Map<String, Object>> findByPostId(Long postId);

    void updateStatus(@Param("id") Long id,
                      @Param("postId") Long postId,
                      @Param("status") String status);

    int countApprovedByPostId(Long postId);

    int existsByPostIdAndUserId(@Param("postId") Long postId,
                                @Param("userId") Long userId);
}
