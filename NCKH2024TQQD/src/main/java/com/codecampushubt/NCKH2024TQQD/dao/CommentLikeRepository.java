package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByCommentCommentIdAndUserUserID(Long commentId, Long userId);
    long countByCommentCommentId(Long commentId);
    void deleteByCommentCommentIdAndUserUserID(Long commentId, Long userId);
}
