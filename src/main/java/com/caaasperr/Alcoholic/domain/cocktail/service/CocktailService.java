package com.caaasperr.Alcoholic.domain.cocktail.service;

import com.caaasperr.Alcoholic._common.annotation.CheckCocktailOwner;
import com.caaasperr.Alcoholic._common.dto.Criteria;
import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic._common.image.ImageHandler;
import com.caaasperr.Alcoholic._common.session.model.CustomUserDetails;
import com.caaasperr.Alcoholic.domain.cocktail.dto.CreateCocktailRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.GetCocktailsResponse;
import com.caaasperr.Alcoholic.domain.cocktail.dto.UpdateCocktailRequest;
import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.comment.repository.CommentRepository;
import com.caaasperr.Alcoholic.domain.rating.repository.RatingRepository;
import com.caaasperr.Alcoholic.domain.user.model.User;
import com.caaasperr.Alcoholic.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final UserRepository userRepository;
    private final ImageHandler imageHandler;
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;

    private final String VIEW_KEY_PREFIX = "view:";
    private final RedisTemplate<String, String> redisTemplate;

    public CocktailService(CocktailRepository cocktailRepository, UserRepository userRepository, ImageHandler imageHandler, RatingRepository ratingRepository, CommentRepository commentRepository, RedisTemplate<String, String> redisTemplate) {
        this.cocktailRepository = cocktailRepository;
        this.userRepository = userRepository;
        this.imageHandler = imageHandler;
        this.ratingRepository = ratingRepository;
        this.commentRepository = commentRepository;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public void createCocktail(CreateCocktailRequest request, Authentication authentication) throws IOException {
        String imagePath = request.cover_image() != null ? imageHandler.saveImage(request.cover_image()) : null;
        cocktailRepository.save(
                request.toCocktail(
                        userRepository.findById(((CustomUserDetails) authentication.getPrincipal()).getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)),
                        imagePath
                )
        );
    }

    public GetCocktailsResponse getCocktails(
            Integer page, Integer size, boolean match,
            List<String> tags, List<String> ingredients,
            String username
    ) {
        Long userId = null;
        if (username != null && !username.isBlank()) {
            userId = userRepository.findByUsername(username)
                    .map(User::getId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        }

        long total;

        if ((tags == null || tags.isEmpty()) && (ingredients == null || ingredients.isEmpty())) {
            total = (userId == null) ? cocktailRepository.count() : cocktailRepository.countByUser_Id(userId);
        } else if (match) {
            List<Long> result = (userId == null)
                    ? cocktailRepository.countByAllTagsAndAllIngredients(
                    tags != null ? tags : Collections.emptyList(),
                    ingredients != null ? ingredients : Collections.emptyList(),
                    tags != null ? tags.size() : 0,
                    ingredients != null ? ingredients.size() : 0
            )
                    : cocktailRepository.countByAllTagsAndAllIngredientsWithUser(
                    tags != null ? tags : Collections.emptyList(),
                    ingredients != null ? ingredients : Collections.emptyList(),
                    tags != null ? tags.size() : 0,
                    ingredients != null ? ingredients.size() : 0,
                    userId
            );
            total = result.size();
        } else {
            total = (userId == null)
                    ? cocktailRepository.countByAnyTagsOrAnyIngredients(
                    tags != null ? tags : Collections.emptyList(),
                    ingredients != null ? ingredients : Collections.emptyList()
            )
                    : cocktailRepository.countByAnyTagsOrAnyIngredientsWithUser(
                    tags != null ? tags : Collections.emptyList(),
                    ingredients != null ? ingredients : Collections.emptyList(),
                    userId
            );
        }

        Criteria criteria = Criteria.of(page, size, (int) total);
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());

        Page<Cocktail> cocktailPage;

        boolean hasTags = tags != null && !tags.isEmpty();
        boolean hasIngredients = ingredients != null && !ingredients.isEmpty();

        if (match) {
            if (hasTags && hasIngredients) {
                cocktailPage = (userId == null)
                        ? cocktailRepository.findByAllTagsAndAllIngredientsNative(tags, ingredients, tags.size(), ingredients.size(), pageable)
                        : cocktailRepository.findByAllTagsAndAllIngredientsNativeWithUser(tags, ingredients, tags.size(), ingredients.size(), userId, pageable);
            } else if (hasTags) {
                cocktailPage = (userId == null)
                        ? cocktailRepository.findByAllTagsOnly(tags, tags.size(), pageable)
                        : cocktailRepository.findByAllTagsOnlyWithUser(tags, tags.size(), userId, pageable);
            } else if (hasIngredients) {
                cocktailPage = (userId == null)
                        ? cocktailRepository.findByAllIngredientsOnly(ingredients, ingredients.size(), pageable)
                        : cocktailRepository.findByAllIngredientsOnlyWithUser(ingredients, ingredients.size(), userId, pageable);
            } else {
                cocktailPage = (userId == null)
                        ? cocktailRepository.findAll(pageable)
                        : cocktailRepository.findByUser_Id(userId, pageable);
            }
        } else {
            if (hasTags || hasIngredients) {
                cocktailPage = (userId == null)
                        ? cocktailRepository.findByAnyTagsOrAnyIngredients(tags, ingredients, pageable)
                        : cocktailRepository.findByAnyTagsOrAnyIngredientsWithUser(tags, ingredients, userId, pageable);
            } else {
                cocktailPage = (userId == null)
                        ? cocktailRepository.findAll(pageable)
                        : cocktailRepository.findByUser_Id(userId, pageable);
            }
        }

        return getGetCocktailsResponse(criteria, cocktailPage);
    }



    public Cocktail getCocktail(Long id) {
        return cocktailRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));
    }

    public GetCocktailsResponse searchByName(String query, Integer page, Integer size) {
        if (query == null || query.trim().isEmpty()) {
            return new GetCocktailsResponse(List.of(), page, size, 0, 0);
        }

        long total = cocktailRepository.countByNameContaining(query);
        Criteria criteria = Criteria.of(page, size, (int) total);
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());

        Page<Cocktail> cocktailPage = cocktailRepository.findByNameContaining(query, pageable);
        return getGetCocktailsResponse(criteria, cocktailPage);
    }

    private GetCocktailsResponse getGetCocktailsResponse(Criteria criteria, Page<Cocktail> cocktailPage) {
        List<Long> cocktailIds = cocktailPage.getContent().stream()
                .map(Cocktail::getId)
                .toList();

        Map<Long, Float> averageRatings = ratingRepository.findAverageRatingByCocktailIds(cocktailIds).stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> ((Double) row[1]).floatValue()
                ));

        return GetCocktailsResponse.of(cocktailPage, averageRatings, criteria);
    }

    @CheckCocktailOwner
    @Transactional
    public void updateCocktail(Long id, UpdateCocktailRequest request, Authentication authentication) throws IOException {
        Cocktail cocktail = cocktailRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));

        if (request.name() != null && !cocktail.getName().equals(request.name())) {
            cocktail.updateName(request.name());
        }

        if (request.description() != null && !cocktail.getDescription().equals(request.description())) {
            cocktail.updateDescription(request.description());
        }

        MultipartFile newImageFile = request.cover_image();

        if (newImageFile != null && !newImageFile.isEmpty()) {
            String newFilename = newImageFile.getOriginalFilename();
            String expectedPath = "/uploads/" + newFilename;
            String oldImage = cocktail.getCover_image();

            if (oldImage == null || !oldImage.equals(expectedPath)) {
                String imagePath = imageHandler.saveImage(newImageFile);
                cocktail.updateCover_image(imagePath);
            }
        }

        if (request.vol() != null && !cocktail.getVol().equals(request.vol())) {
            cocktail.updateVol(request.vol());
        }

        cocktail.updateUpdated_at(LocalDateTime.now());
    }

    @CheckCocktailOwner
    public void deleteCocktail(Long id, Authentication authentication) {
        commentRepository.deleteByCocktail_Id(id);
        ratingRepository.deleteByCocktail_Id(id);
        cocktailRepository.deleteById(id);
    }

    //Views
    public void increaseViewCount(Long id, String ip) {
        String key = VIEW_KEY_PREFIX + ip + ":cocktail:" + id;

        Boolean conflictCheck = redisTemplate.opsForValue().setIfAbsent(key, "1", Duration.ofHours(1));

        if (Boolean.TRUE.equals(conflictCheck)) {
            String redisKey = VIEW_KEY_PREFIX + "cocktail:" + id;
            redisTemplate.opsForValue().increment(redisKey);
        }
    }

    @Transactional
    @Scheduled(cron = "0 1/3 * * * *")
    public void syncViewCount() {
        Set<String> keys = redisTemplate.keys(VIEW_KEY_PREFIX + "cocktail:*");

        if (!keys.isEmpty()) {
            for (String key : keys) {
                String[] parts = key.split(":");

                Long cocktailId = Long.parseLong(parts[2]);
                String countRaw = redisTemplate.opsForValue().get(key);

                if (countRaw != null) {
                    long count = Long.parseLong(countRaw);
                    cocktailRepository.increaseViews(cocktailId, count);
                    redisTemplate.delete(key);
                }
            }
        }
    }
}
