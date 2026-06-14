package com.travory.app.post.mapper;

import com.travory.app.post.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

import java.util.List;

@Mapper
public interface PostMapper {

    void insertPost(PostDto postDto);

    List<PostDto> findAll();

    int countPosts(@Param("keyword") String keyword);

    PostDto findById(Long id);

    void increaseViewCount(Long id);

    List<Map<String, Object>> findAllWithUser();

    List<Map<String, Object>> findPostsPaged(@Param("keyword") String keyword,
                                             @Param("offset") int offset,
                                             @Param("size") int size);

    void updatePost(PostDto postDto);

    void updatePostStatus(@Param("id") Long id,
                          @Param("userId") Long userId,
                          @Param("status") String status);

    void deletePost(Long id);
}
