package com.travory.app.mypage.controller;

import com.travory.app.global.exception.BadRequestException;
import com.travory.app.mypage.service.MyPageService;
import com.travory.app.user.dto.UserDto;
import com.travory.app.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private static final List<String> TRAVEL_STYLE_OPTIONS =
            List.of("자유여행", "맛집투어", "사진여행", "액티비티", "휴양",
                    "배낭여행", "쇼핑", "자연/힐링", "역사/문화", "럭셔리");
    private static final List<String> FAVORITE_COUNTRY_OPTIONS =
            List.of("일본", "미국", "프랑스", "이탈리아", "스페인",
                    "태국", "베트남", "대만", "호주", "스위스");
    private static final int MAX_PROFILE_OPTION_COUNT = 4;

    private final MyPageService myPageService;
    private final UserService userService;

    @GetMapping("/mypage")
    public String myPage(HttpSession session,
                         Model model) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        model.addAttribute("loginUser", loginUser);
        model.addAttribute(
                "myPostList",
                myPageService.getMyPosts(loginUser.getId())
        );
        model.addAttribute(
                "favoritePostList",
                myPageService.getFavoritePosts(loginUser.getId())
        );
        model.addAttribute(
                "companionRequestList",
                myPageService.getCompanionRequests(loginUser.getId())
        );
        model.addAttribute(
                "statistics",
                myPageService.getStatistics(loginUser.getId())
        );

        return "mypage/index";
    }

    @GetMapping("/mypage/profile/edit")
    public String profileEditPage(HttpSession session,
                                  Model model) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        UserDto user =
                userService.getUserById(loginUser.getId());

        model.addAttribute("user", user);
        model.addAttribute("travelStyleOptions", TRAVEL_STYLE_OPTIONS);
        model.addAttribute("favoriteCountryOptions", FAVORITE_COUNTRY_OPTIONS);
        model.addAttribute("selectedTravelStyles", splitValues(user.getTravelStyle()));
        model.addAttribute("selectedFavoriteCountries", splitValues(user.getFavoriteCountry()));

        return "user/profile-edit";
    }

    @PostMapping("/mypage/profile/edit")
    public String profileEdit(UserDto userDto,
                              @RequestParam(value = "travelStyleValues", required = false)
                              List<String> travelStyleValues,
                              @RequestParam(value = "favoriteCountryValues", required = false)
                              List<String> favoriteCountryValues,
                              @RequestParam(value = "profileImageFile", required = false)
                              MultipartFile profileImageFile,
                              HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        userDto.setId(loginUser.getId());
        userDto.setTravelStyle(joinSelectedValues(travelStyleValues, TRAVEL_STYLE_OPTIONS));
        userDto.setFavoriteCountry(joinSelectedValues(favoriteCountryValues, FAVORITE_COUNTRY_OPTIONS));
        userService.updateProfile(userDto, profileImageFile);

        UserDto updatedUser =
                userService.getUserById(loginUser.getId());

        session.setAttribute("loginUser", updatedUser);

        return "redirect:/mypage";
    }

    private List<String> splitValues(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }

        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .toList();
    }

    private String joinSelectedValues(List<String> selectedValues,
                                      List<String> allowedValues) {

        if (selectedValues == null || selectedValues.isEmpty()) {
            return "";
        }

        List<String> filteredValues =
                selectedValues.stream()
                        .filter(allowedValues::contains)
                        .distinct()
                        .toList();

        if (filteredValues.size() > MAX_PROFILE_OPTION_COUNT) {
            throw new BadRequestException("여행 스타일과 선호 국가는 각각 최대 4개까지 선택할 수 있습니다.");
        }

        return String.join(", ", filteredValues);
    }
}
