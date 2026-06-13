package com.travory.app.companion.service;

import com.travory.app.companion.mapper.CompanionMapper;
import com.travory.app.post.dto.PostDto;
import com.travory.app.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CompanionServiceImpl implements CompanionService {

    private final CompanionMapper companionMapper;
    private final PostMapper postMapper;

    @Override
    public void requestCompanion(Long postId, Long userId, String message) {
        PostDto post = postMapper.findById(postId);

        if (post == null) {
            return;
        }

        if (post.getUserId().equals(userId)) {
            return;
        }

        if (!hasRequested(postId, userId)) {
            companionMapper.insertRequest(postId, userId, message);
        }
    }

    @Override
    public List<Map<String, Object>> getRequests(Long postId, Long ownerId) {
        if (!isPostOwner(postId, ownerId)) {
            return List.of();
        }

        return companionMapper.findByPostId(postId);
    }

    @Override
    public void approveRequest(Long postId, Long requestId, Long ownerId) {
        updateRequestStatus(postId, requestId, ownerId, "APPROVED");
    }

    @Override
    public void rejectRequest(Long postId, Long requestId, Long ownerId) {
        updateRequestStatus(postId, requestId, ownerId, "REJECTED");
    }

    @Override
    public int getParticipantCount(Long postId) {
        return companionMapper.countApprovedByPostId(postId);
    }

    @Override
    public boolean hasRequested(Long postId, Long userId) {
        return companionMapper.existsByPostIdAndUserId(postId, userId) > 0;
    }

    private void updateRequestStatus(Long postId,
                                     Long requestId,
                                     Long ownerId,
                                     String status) {

        if (!isPostOwner(postId, ownerId)) {
            return;
        }

        companionMapper.updateStatus(requestId, postId, status);
    }

    private boolean isPostOwner(Long postId, Long ownerId) {
        PostDto post = postMapper.findById(postId);

        return post != null && post.getUserId().equals(ownerId);
    }
}
