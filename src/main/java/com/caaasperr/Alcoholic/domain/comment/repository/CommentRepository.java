package com.caaasperr.Alcoholic.domain.comment.repository;

import com.caaasperr.Alcoholic.domain.comment.model.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"user"})
    List<Comment> findAllByCocktail_Id(Long cocktailId);

    void deleteByUser_Id(Long userId);

    void deleteByCocktail_Id(Long cocktailId);
}
