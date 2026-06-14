package com.travory.app.comment.service;

import com.travory.app.comment.dto.CommentDto;
import com.travory.app.comment.mapper.CommentMapper;
import com.travory.app.notification.service.NotificationService;
import com.travory.app.post.dto.PostDto;
import com.travory.app.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final PostMapper postMapper;
    private final NotificationService notificationService;

    @Override
    public void createComment(CommentDto commentDto) {
        commentMapper.insertComment(commentDto);

        PostDto post =
                postMapper.findById(commentDto.getPostId());

        if (post == null) {
            return;
        }

        notificationService.createNotification(
                post.getUserId(),
                commentDto.getUserId(),
                "COMMENT",
                "게시글에 새 댓글이 작성되었습니다.",
                "/posts/" + commentDto.getPostId()
        );
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
