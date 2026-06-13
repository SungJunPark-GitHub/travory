package com.travory.app.comment.mapper;

import com.travory.app.comment.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    void insertComment(CommentDto commentDto);

    List<CommentDto> findByPostId(Long postId);

    CommentDto findById(Long id);

    void deleteComment(Long id);
}