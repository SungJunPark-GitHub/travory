package com.travory.app.mypage.service;

import com.travory.app.mypage.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MyPageMapper myPageMapper;

    @Override
    public List<Map<String, Object>> getMyPosts(Long userId) {
        return myPageMapper.findPostsByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> getFavoritePosts(Long userId) {
        return myPageMapper.findFavoritePostsByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> getCompanionRequests(Long userId) {
        return myPageMapper.findCompanionRequestsByUserId(userId);
    }
}
