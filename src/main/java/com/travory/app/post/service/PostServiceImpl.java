package com.travory.app.post.service;

import com.travory.app.global.exception.BadRequestException;
import com.travory.app.global.exception.NotFoundException;
import com.travory.app.post.dto.PostDto;
import com.travory.app.post.mapper.PostMapper;
import com.travory.app.user.dto.UserDto;
import com.travory.app.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final List<String> POST_STATUSES =
            List.of("OPEN", "CLOSED", "FINISHED");
    private static final List<String> SORT_TYPES =
            List.of("latest", "view", "like");
    private static final long MAX_POST_IMAGE_SIZE = 5 * 1024 * 1024;
    private static final Path POST_UPLOAD_DIR =
            Path.of("uploads", "posts");
    private static final Set<String> ALLOWED_IMAGE_TYPES =
            Set.of("image/jpeg", "image/png", "image/gif", "image/webp");

    private final PostMapper postMapper;
    private final UserMapper userMapper;

    @Override
    public void createPost(PostDto postDto) {
        postMapper.insertPost(postDto);
    }

    @Override
    public void createPost(PostDto postDto, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            postDto.setImagePath(savePostImage(imageFile));
        }

        postMapper.insertPost(postDto);
    }

    @Override
    public List<Map<String, Object>> getPostList(String keyword, String sort, int page, int size) {
        int offset = (page - 1) * size;
        return postMapper.findPostsPaged(keyword, getSortType(sort), offset, size);
    }

    @Override
    public int countPosts(String keyword) {
        return postMapper.countPosts(keyword);
    }

    @Override
    public String getSortType(String sort) {
        if (SORT_TYPES.contains(sort)) {
            return sort;
        }

        return "latest";
    }

    @Override
    public PostDto getPostDetail(Long id) {
        postMapper.increaseViewCount(id);
        PostDto post =
                postMapper.findById(id);

        if (post == null) {
            throw new NotFoundException("게시글을 찾을 수 없습니다.");
        }

        return post;
    }

    @Override
    public PostDto getPostById(Long id) {
        PostDto post =
                postMapper.findById(id);

        if (post == null) {
            throw new NotFoundException("게시글을 찾을 수 없습니다.");
        }

        return post;
    }

    @Override
    public UserDto getPostAuthor(Long postId) {
        PostDto post =
                getPostById(postId);

        return userMapper.findById(post.getUserId());
    }

    @Override
    public void updatePost(PostDto postDto) {
        postMapper.updatePost(postDto);
    }

    @Override
    public void updatePost(PostDto postDto, MultipartFile imageFile) {
        PostDto currentPost =
                getPostById(postDto.getId());

        postDto.setImagePath(currentPost.getImagePath());

        if (imageFile != null && !imageFile.isEmpty()) {
            postDto.setImagePath(savePostImage(imageFile));
        }

        postMapper.updatePost(postDto);
    }

    @Override
    public void updatePostStatus(Long id, Long userId, String status) {
        if (!POST_STATUSES.contains(status)) {
            throw new BadRequestException("유효하지 않은 모집 상태입니다.");
        }

        postMapper.updatePostStatus(id, userId, status);
    }

    @Override
    public void deletePost(Long id) {
        postMapper.deletePost(id);
    }

    private String savePostImage(MultipartFile file) {
        validatePostImage(file);

        try {
            Path uploadDir =
                    POST_UPLOAD_DIR.toAbsolutePath().normalize();

            Files.createDirectories(uploadDir);

            String extension =
                    getExtension(file.getOriginalFilename());
            String savedFilename =
                    UUID.randomUUID() + extension;
            Path destination =
                    uploadDir.resolve(savedFilename).normalize();

            file.transferTo(destination);

            return "/uploads/posts/" + savedFilename;
        } catch (IOException e) {
            throw new BadRequestException("게시글 이미지를 저장할 수 없습니다.");
        }
    }

    private void validatePostImage(MultipartFile file) {
        if (file.getSize() > MAX_POST_IMAGE_SIZE) {
            throw new BadRequestException("게시글 이미지는 5MB 이하만 업로드할 수 있습니다.");
        }

        String contentType =
                file.getContentType();

        if (contentType == null ||
                !ALLOWED_IMAGE_TYPES.contains(contentType.toLowerCase(Locale.ROOT))) {
            throw new BadRequestException("이미지 파일만 업로드할 수 있습니다.");
        }

        if (getExtension(file.getOriginalFilename()).isBlank()) {
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
