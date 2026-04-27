package com.codecampushubt.NCKH2024TQQD.service.PostServices;

import com.codecampushubt.NCKH2024TQQD.dao.PostRepository;
import com.codecampushubt.NCKH2024TQQD.dao.UserRepository;
import com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogPostDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Post;
import com.codecampushubt.NCKH2024TQQD.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BlogPostDTO> getAllBlogPosts() {
        return postRepository.findAllBlogPosts();
    }

    @Override
    public List<BlogPostDTO> getPopularPosts(int limit) {
        return postRepository.findPopularPosts(PageRequest.of(0, limit));
    }

    @Override
    public List<BlogPostDTO> getLatestPosts(int limit) {
        return postRepository.findLatestPosts(PageRequest.of(0, limit));
    }

    @Override
    public BlogPostDTO getBlogPostBySlug(String slug) {
        Post post = postRepository.findBySlugAndDeletedAtIsNull(slug)
                .orElseThrow(() -> new RuntimeException("Bài viết không tồn tại: " + slug));

        BlogPostDTO dto = new BlogPostDTO();
        dto.setPostId(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setSlug(post.getSlug());
        dto.setThumbnailUrl(post.getThumbnailUrl());
        dto.setContent(post.getContent());
        dto.setType(post.getType());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setLikesCount(post.getLikesCount());
        dto.setCommentsCount(post.getCommentsCount());

        if (post.getUser() != null) {
            dto.setAuthorName(post.getUser().getFullName());
            dto.setAuthorImage(post.getUser().getImage());
        }

        return dto;
    }

    @Override
    public void createBlogPost(Long userId, String title, String thumbnailUrl, String content, String type) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng: " + userId));

        LocalDateTime now = LocalDateTime.now();
        Post post = new Post();
        post.setUser(author);
        post.setTitle(title.trim());
        post.setSlug(generateUniqueSlug(title));
        post.setThumbnailUrl((thumbnailUrl == null || thumbnailUrl.isBlank()) ? null : thumbnailUrl.trim());
        post.setContent(content.trim());
        post.setType((type == null || type.isBlank()) ? "regular" : type.trim());
        post.setVisibility("public");
        post.setCreatedAt(now);
        post.setUpdatedAt(now);
        post.setLikesCount(0);
        post.setCommentsCount(0);
        post.setSharesCount(0);
        post.setReportsCount(0);

        postRepository.save(post);
    }

    private String generateUniqueSlug(String title) {
        String baseSlug = normalizeToSlug(title);
        String slug = baseSlug;
        int suffix = 1;

        while (postRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + suffix++;
        }
        return slug;
    }

    private String normalizeToSlug(String value) {
        String normalized = value.toLowerCase(Locale.ROOT).trim();
        normalized = normalized.replaceAll("[^a-z0-9\\s-]", "");
        normalized = normalized.replaceAll("\\s+", "-");
        normalized = normalized.replaceAll("-+", "-");
        normalized = normalized.replaceAll("^-|-$", "");
        return normalized.isBlank() ? "blog-post" : normalized;
    }
}
