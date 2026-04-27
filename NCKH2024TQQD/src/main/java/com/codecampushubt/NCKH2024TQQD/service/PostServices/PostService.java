package com.codecampushubt.NCKH2024TQQD.service.PostServices;

import com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogPostDTO;
import com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogCommentDTO;

import java.util.List;

public interface PostService {
    List<BlogPostDTO> getAllBlogPosts();
    List<BlogPostDTO> getPopularPosts(int limit);
    List<BlogPostDTO> getLatestPosts(int limit);
    BlogPostDTO getBlogPostBySlug(String slug);
    List<BlogCommentDTO> getCommentsByPostSlug(String slug, Long currentUserId);
    void addComment(String slug, Long userId, String content);
    void toggleCommentLike(String slug, Long commentId, Long userId);
    boolean isPostAuthor(String slug, Long userId);
    void updateBlogPost(String slug, Long userId, String title, String thumbnailUrl, String content, String type);
    void createBlogPost(Long userId, String title, String thumbnailUrl, String content, String type);
}
