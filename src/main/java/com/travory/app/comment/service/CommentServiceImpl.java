package com.travory.app.comment.service;

import com.travory.app.comment.dto.CommentDto;
import com.travory.app.comment.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public void createComment(CommentDto commentDto) {
        commentMapper.insertComment(commentDto);
    }

    @Override
    public List<CommentDto> getComments(Long postId) {
        return commentMapper.findByPostId(postId);
    }

    @Override
    public CommentDto getCommentById(Long id) {
        return commentMapper.findById(id);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentMapper.deleteComment(commentId);
    }
}
