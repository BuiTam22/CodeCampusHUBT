package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogPostDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Lay tat ca bai viet chua bi xoa, moi nhat truoc - tra ve DTO
    @Query("SELECT new com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogPostDTO(" +
            "p.postId, p.title, p.slug, p.thumbnailUrl, p.content, " +
            "p.type, u.fullName, u.image, " +
            "p.createdAt, p.likesCount, p.commentsCount) " +
            "FROM Post p LEFT JOIN p.user u " +
            "WHERE p.deletedAt IS NULL AND p.visibility = 'public' " +
            "ORDER BY p.createdAt DESC")
    List<BlogPostDTO> findAllBlogPosts();

    // Lay bai viet pho bien nhat (theo luot like)
    @Query("SELECT new com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogPostDTO(" +
            "p.postId, p.title, p.slug, p.thumbnailUrl, p.content, " +
            "p.type, u.fullName, u.image, " +
            "p.createdAt, p.likesCount, p.commentsCount) " +
            "FROM Post p LEFT JOIN p.user u " +
            "WHERE p.deletedAt IS NULL AND p.visibility = 'public' " +
            "ORDER BY p.likesCount DESC")
    List<BlogPostDTO> findPopularPosts(Pageable pageable);

    // Lay bai viet moi nhat voi gioi han so luong
    @Query("SELECT new com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogPostDTO(" +
            "p.postId, p.title, p.slug, p.thumbnailUrl, p.content, " +
            "p.type, u.fullName, u.image, " +
            "p.createdAt, p.likesCount, p.commentsCount) " +
            "FROM Post p LEFT JOIN p.user u " +
            "WHERE p.deletedAt IS NULL AND p.visibility = 'public' " +
            "ORDER BY p.createdAt DESC")
    List<BlogPostDTO> findLatestPosts(Pageable pageable);

    // Tim bai viet theo slug
    Optional<Post> findBySlugAndDeletedAtIsNull(String slug);
}
