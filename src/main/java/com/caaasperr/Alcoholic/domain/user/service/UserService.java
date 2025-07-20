package com.caaasperr.Alcoholic.domain.user.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic._common.session.model.CustomUserDetails;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.comment.repository.CommentRepository;
import com.caaasperr.Alcoholic.domain.rating.repository.RatingRepository;
import com.caaasperr.Alcoholic.domain.user.dto.GetProfileResponse;
import com.caaasperr.Alcoholic.domain.user.dto.GetUserResponse;
import com.caaasperr.Alcoholic.domain.user.dto.RegisterRequest;
import com.caaasperr.Alcoholic.domain.user.model.User;
import com.caaasperr.Alcoholic.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CocktailRepository cocktailRepository;
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CocktailRepository cocktailRepository, RatingRepository ratingRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cocktailRepository = cocktailRepository;
        this.ratingRepository = ratingRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void register(RegisterRequest request) {
        userRepository.save(request.toUser(passwordEncoder));
    }

    public GetUserResponse getUser(String username) {
        return GetUserResponse.of(userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)));
    }

    public GetProfileResponse getProfile(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Long cocktailCount = cocktailRepository.countByUser_Id(user.getId());
        Long ratingCount = ratingRepository.countByUser_Id(user.getId());

        return new GetProfileResponse(username, cocktailCount, ratingCount, user.getCreatedAt());
    }

    @Transactional
    public void patchPassword(String username, String password)  {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        user.updatePassword(passwordEncoder.encode(password));
    }

    @Transactional
    public void deleteUser(Long id, CustomUserDetails customUserDetails) {
        if (customUserDetails.getId().equals(id)) {
            cocktailRepository.deleteByUser_Id(id);
            commentRepository.deleteByUser_Id(id);
            ratingRepository.deleteByUser_Id(id);
            
            userRepository.deleteById(id);
        } else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
    }
}
