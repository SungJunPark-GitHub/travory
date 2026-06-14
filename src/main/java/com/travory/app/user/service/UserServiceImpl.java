package com.travory.app.user.service;

import com.travory.app.global.exception.BadRequestException;
import com.travory.app.user.dto.UserDto;
import com.travory.app.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final long MAX_PROFILE_IMAGE_SIZE = 5 * 1024 * 1024;
    private static final Path PROFILE_UPLOAD_DIR =
            Path.of("uploads", "profile");
    private static final Set<String> ALLOWED_IMAGE_TYPES =
            Set.of("image/jpeg", "image/png", "image/gif", "image/webp");

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(UserDto userDto) {

        if (userMapper.findByUserId(userDto.getUserId()) != null) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        if (userMapper.findByEmail(userDto.getEmail()) != null) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        userDto.setPassword(
                passwordEncoder.encode(userDto.getPassword())
        );

        userMapper.insertUser(userDto);
    }

    @Override
    public UserDto login(UserDto userDto) {

        UserDto findUser =
                userMapper.findByUserId(userDto.getUserId());

        if (findUser == null) {
            return null;
        }

        boolean match =
                passwordEncoder.matches(
                        userDto.getPassword(),
                        findUser.getPassword()
                );

        if (!match) {
            return null;
        }

        return findUser;
    }

    @Override
    public UserDto getUserById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public void updateProfile(UserDto userDto) {
        userMapper.updateProfile(userDto);
    }

    @Override
    public void updateProfile(UserDto userDto, MultipartFile profileImageFile) {
        UserDto currentUser =
                userMapper.findById(userDto.getId());

        if (currentUser != null) {
            userDto.setProfileImage(currentUser.getProfileImage());
        }

        if (profileImageFile != null && !profileImageFile.isEmpty()) {
            userDto.setProfileImage(saveProfileImage(profileImageFile));
        }

        userMapper.updateProfile(userDto);
    }

    private String saveProfileImage(MultipartFile file) {
        validateProfileImage(file);

        try {
            Path uploadDir =
                    PROFILE_UPLOAD_DIR.toAbsolutePath().normalize();

            Files.createDirectories(uploadDir);

            String originalFilename =
                    file.getOriginalFilename();
            String extension =
                    getExtension(originalFilename);
            String savedFilename =
                    UUID.randomUUID() + extension;
            Path destination =
                    uploadDir.resolve(savedFilename).normalize();

            file.transferTo(destination);

            return "/uploads/profile/" + savedFilename;
        } catch (IOException e) {
            throw new BadRequestException("프로필 이미지를 저장할 수 없습니다.");
        }
    }

    private void validateProfileImage(MultipartFile file) {
        if (file.getSize() > MAX_PROFILE_IMAGE_SIZE) {
            throw new BadRequestException("프로필 이미지는 5MB 이하만 업로드할 수 있습니다.");
        }

        String contentType =
                file.getContentType();

        if (contentType == null ||
                !ALLOWED_IMAGE_TYPES.contains(contentType.toLowerCase(Locale.ROOT))) {
            throw new BadRequestException("이미지 파일만 업로드할 수 있습니다.");
        }

        String extension =
                getExtension(file.getOriginalFilename());

        if (extension.isBlank()) {
            throw new BadRequestException("이미지 파일만 업로드할 수 있습니다.");
        }
    }

    private String getExtension(String filename) {
        if (filename == null) {
            return "";
        }

        int dotIndex =
                filename.lastIndexOf(".");

        if (dotIndex < 0 || dotIndex == filename.length() - 1) {
            return "";
        }

        String extension =
                filename.substring(dotIndex).toLowerCase(Locale.ROOT);

        return switch (extension) {
            case ".jpg", ".jpeg", ".png", ".gif", ".webp" -> extension;
            default -> "";
        };
    }
}
