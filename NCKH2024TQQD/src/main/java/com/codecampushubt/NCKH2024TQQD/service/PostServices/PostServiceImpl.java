package com.codecampushubt.NCKH2024TQQD.service.PostServices;

import com.codecampushubt.NCKH2024TQQD.dao.CommentLikeRepository;
import com.codecampushubt.NCKH2024TQQD.dao.CommentRepository;
import com.codecampushubt.NCKH2024TQQD.dao.PostRepository;
import com.codecampushubt.NCKH2024TQQD.dao.UserRepository;
import com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogCommentDTO;
import com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogPostDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Comment;
import com.codecampushubt.NCKH2024TQQD.entity.CommentLike;
import com.codecampushubt.NCKH2024TQQD.entity.Post;
import com.codecampushubt.NCKH2024TQQD.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           UserRepository userRepository,
                           CommentRepository commentRepository,
                           CommentLikeRepository commentLikeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
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
    public List<BlogCommentDTO> getCommentsByPostSlug(String slug, Long currentUserId) {
        Post post = getPostBySlugOrThrow(slug);
        List<Comment> comments = commentRepository
                .findByPostPostIdAndParentCommentIsNullAndIsDeletedFalseOrderByCreatedAtDesc(post.getPostId());
        List<BlogCommentDTO> result = new ArrayList<>();
        for (Comment comment : comments) {
            BlogCommentDTO dto = new BlogCommentDTO();
            dto.setCommentId(comment.getCommentId());
            dto.setContent(comment.getContent());
            dto.setCreatedAt(comment.getCreatedAt());
            dto.setLikesCount(comment.getLikesCount() == null ? 0 : comment.getLikesCount());

            User author = comment.getUser();
            dto.setAuthorName(author != null ? author.getFullName() : "Ẩn danh");
            dto.setAuthorImage(author != null ? author.getImage() : null);

            boolean liked = currentUserId != null
                    && commentLikeRepository.existsByCommentCommentIdAndUserUserID(comment.getCommentId(), currentUserId);
            dto.setLikedByCurrentUser(liked);
            result.add(dto);
        }
        return result;
    }

    @Override
    @Transactional
    public void addComment(String slug, Long userId, String content) {
        Post post = getPostBySlugOrThrow(slug);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng: " + userId));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content.trim());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setLikesCount(0);
        comment.setRepliesCount(0);
        comment.setDeleted(false);
        comment.setEdited(false);
        commentRepository.save(comment);

        post.setCommentsCount((post.getCommentsCount() == null ? 0 : post.getCommentsCount()) + 1);
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void toggleCommentLike(String slug, Long commentId, Long userId) {
        Post post = getPostBySlugOrThrow(slug);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bình luận: " + commentId));
        if (comment.getPost() == null || !comment.getPost().getPostId().equals(post.getPostId())) {
            throw new RuntimeException("Bình luận không thuộc bài viết.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng: " + userId));

        boolean liked = commentLikeRepository.existsByCommentCommentIdAndUserUserID(commentId, userId);
        if (liked) {
            commentLikeRepository.deleteByCommentCommentIdAndUserUserID(commentId, userId);
        } else {
            CommentLike commentLike = new CommentLike();
            commentLike.setComment(comment);
            commentLike.setUser(user);
            commentLike.setCreatedAt(LocalDateTime.now());
            commentLikeRepository.save(commentLike);
        }

        comment.setLikesCount((int) commentLikeRepository.countByCommentCommentId(commentId));
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    public boolean isPostAuthor(String slug, Long userId) {
        if (userId == null) {
            return false;
        }
        Post post = getPostBySlugOrThrow(slug);
        return post.getUser() != null && userId.equals(post.getUser().getUserId());
    }

    @Override
    @Transactional
    public void updateBlogPost(String slug, Long userId, String title, String thumbnailUrl, String content, String type) {
        Post post = getPostBySlugOrThrow(slug);
        if (post.getUser() == null || !userId.equals(post.getUser().getUserId())) {
            throw new RuntimeException("Bạn không có quyền chỉnh sửa bài viết này.");
        }

        post.setTitle(title.trim());
        post.setThumbnailUrl((thumbnailUrl == null || thumbnailUrl.isBlank()) ? null : thumbnailUrl.trim());
        post.setContent(content.trim());
        post.setType((type == null || type.isBlank()) ? "regular" : type.trim());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
    }

    private Post getPostBySlugOrThrow(String slug) {
        return postRepository.findBySlugAndDeletedAtIsNull(slug)
                .orElseThrow(() -> new RuntimeException("Bài viết không tồn tại: " + slug));
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
