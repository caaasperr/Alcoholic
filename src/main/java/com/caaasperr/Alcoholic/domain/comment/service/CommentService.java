package com.caaasperr.Alcoholic.domain.comment.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.comment.dto.CreateCommentRequest;
import com.caaasperr.Alcoholic.domain.comment.dto.GetCommentResponse;
import com.caaasperr.Alcoholic.domain.comment.model.Comment;
import com.caaasperr.Alcoholic.domain.comment.repository.CommentRepository;
import com.caaasperr.Alcoholic.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CocktailRepository cocktailRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, CocktailRepository cocktailRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.cocktailRepository = cocktailRepository;
        this.userRepository = userRepository;
    }

    public void createComment(CreateCommentRequest request) {
        commentRepository.save(request.toComment(
                cocktailRepository.findById(request.cocktail_id()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL)),
                userRepository.findByUsername(request.username()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)))
        );
    }

    public GetCommentResponse getComment(Long id) {
        return GetCommentResponse.of(commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<GetCommentResponse> getCommentByCocktail(Long id) {
        return commentRepository.findAllByCocktail_Id(id).stream().map(GetCommentResponse::of).toList();
    }
}
