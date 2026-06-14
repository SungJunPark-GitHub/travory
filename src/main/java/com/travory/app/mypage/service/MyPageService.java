package com.travory.app.mypage.service;

import java.util.List;
import java.util.Map;

public interface MyPageService {

    List<Map<String, Object>> getMyPosts(Long userId);

    List<Map<String, Object>> getFavoritePosts(Long userId);

    List<Map<String, Object>> getCompanionRequests(Long userId);
}
