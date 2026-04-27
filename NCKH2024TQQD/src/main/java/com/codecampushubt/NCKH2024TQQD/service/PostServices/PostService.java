package com.codecampushubt.NCKH2024TQQD.service.PostServices;

import com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogPostDTO;

import java.util.List;

public interface PostService {
    List<BlogPostDTO> getAllBlogPosts();
    List<BlogPostDTO> getPopularPosts(int limit);
    List<BlogPostDTO> getLatestPosts(int limit);
    BlogPostDTO getBlogPostBySlug(String slug);
    void createBlogPost(Long userId, String title, String thumbnailUrl, String content, String type);
}
