package com.caaasperr.Alcoholic.domain.comment.repository;

import com.caaasperr.Alcoholic.domain.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
