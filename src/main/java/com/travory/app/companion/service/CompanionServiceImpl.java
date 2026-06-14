package com.travory.app.companion.service;

import com.travory.app.companion.mapper.CompanionMapper;
import com.travory.app.notification.service.NotificationService;
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
    private final NotificationService notificationService;

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

            notificationService.createNotification(
                    post.getUserId(),
                    userId,
                    "COMPANION_REQUEST",
                    "새로운 동행 신청이 도착했습니다.",
                    "/posts/" + postId + "/requests"
            );
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
    public void cancelRequest(Long postId, Long userId) {
        companionMapper.deletePendingByPostIdAndUserId(postId, userId);
    }

    @Override
    public int getParticipantCount(Long postId) {
        return companionMapper.countApprovedByPostId(postId);
    }

    @Override
    public boolean hasRequested(Long postId, Long userId) {
        return companionMapper.existsByPostIdAndUserId(postId, userId) > 0;
    }

    @Override
    public boolean hasApprovedRequest(Long postId, Long userId) {
        return companionMapper.existsApprovedByPostIdAndUserId(postId, userId) > 0;
    }

    @Override
    public String getRequestStatus(Long postId, Long userId) {
        if (userId == null) {
            return null;
        }

        return companionMapper.findStatusByPostIdAndUserId(postId, userId);
    }

    private void updateRequestStatus(Long postId,
                                     Long requestId,
                                     Long ownerId,
                                     String status) {

        if (!isPostOwner(postId, ownerId)) {
            return;
        }

        companionMapper.updateStatus(requestId, postId, status);

        Map<String, Object> request =
                companionMapper.findById(requestId);

        if (request == null) {
            return;
        }

        Long applicantId =
                ((Number) request.get("user_id")).longValue();

        String message =
                "APPROVED".equals(status)
                        ? "동행 신청이 승인되었습니다."
                        : "동행 신청이 거절되었습니다.";

        notificationService.createNotification(
                applicantId,
                ownerId,
                "COMPANION_" + status,
                message,
                "/posts/" + postId
        );
    }

    private boolean isPostOwner(Long postId, Long ownerId) {
        PostDto post = postMapper.findById(postId);

        return post != null && post.getUserId().equals(ownerId);
    }
}
