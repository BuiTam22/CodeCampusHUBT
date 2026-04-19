package com.codecampushubt.NCKH2024TQQD.service.PostServices;

import com.codecampushubt.NCKH2024TQQD.dao.PostRepository;
import com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogPostDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
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
}
