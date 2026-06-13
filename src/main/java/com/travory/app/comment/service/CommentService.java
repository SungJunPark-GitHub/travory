package com.travory.app.comment.service;

import com.travory.app.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {

    void createComment(CommentDto commentDto);

    List<CommentDto> getComments(Long postId);

    void deleteComment(Long commentId);
}