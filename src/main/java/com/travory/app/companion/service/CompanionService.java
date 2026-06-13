package com.travory.app.companion.service;

import java.util.List;
import java.util.Map;

public interface CompanionService {

    void requestCompanion(Long postId, Long userId, String message);

    List<Map<String, Object>> getRequests(Long postId, Long ownerId);

    void approveRequest(Long postId, Long requestId, Long ownerId);

    void rejectRequest(Long postId, Long requestId, Long ownerId);

    int getParticipantCount(Long postId);

    boolean hasRequested(Long postId, Long userId);
}
