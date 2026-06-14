package com.travory.app.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyPageMapper {

    List<Map<String, Object>> findPostsByUserId(Long userId);

    List<Map<String, Object>> findFavoritePostsByUserId(Long userId);

    List<Map<String, Object>> findCompanionRequestsByUserId(Long userId);

    Map<String, Object> findStatisticsByUserId(Long userId);
}
