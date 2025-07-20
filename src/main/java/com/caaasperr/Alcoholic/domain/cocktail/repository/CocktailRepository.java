package com.caaasperr.Alcoholic.domain.cocktail.repository;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.user.model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
    @Query(value = """
    SELECT c.* FROM cocktail c
    WHERE c.id IN (
        SELECT ct.cocktail_id FROM cocktail_tags ct
        JOIN tag t ON t.id = ct.tag_id
        WHERE t.name IN :tags AND c.user.id = COALESCE(:userId, c.userId)
        GROUP BY ct.cocktail_id
        HAVING COUNT(DISTINCT t.id) = :tagCount
    )
    AND c.id IN (
        SELECT ci.cocktail_id FROM cocktail_ingredients ci
        JOIN ingredient i ON i.id = ci.ingredient_id
        WHERE i.name IN :ingredients AND c.user.id = COALESCE(:userId, c.userId)
        GROUP BY ci.cocktail_id
        HAVING COUNT(DISTINCT i.id) = :ingredientCount
    )
""", nativeQuery = true)
    Page<Cocktail> findByAllTagsAndAllIngredientsNativeWithUser(
            @Param("tags") List<String> tags,
            @Param("ingredients") List<String> ingredients,
            int tagCount,
            int ingredientCount,
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query(value = """
    SELECT c.* FROM cocktail c
    WHERE c.id IN (
        SELECT ct.cocktail_id FROM cocktail_tags ct
        JOIN tag t ON t.id = ct.tag_id
        WHERE t.name IN :tags
        GROUP BY ct.cocktail_id
        HAVING COUNT(DISTINCT t.id) = :tagCount
    )
    AND c.id IN (
        SELECT ci.cocktail_id FROM cocktail_ingredients ci
        JOIN ingredient i ON i.id = ci.ingredient_id
        WHERE i.name IN :ingredients
        GROUP BY ci.cocktail_id
        HAVING COUNT(DISTINCT i.id) = :ingredientCount
    )
""", nativeQuery = true)
    Page<Cocktail> findByAllTagsAndAllIngredientsNative(
            @Param("tags") List<String> tags,
            @Param("ingredients") List<String> ingredients,
            int tagCount,
            int ingredientCount,
            Pageable pageable
    );

    @Query(value = """
    SELECT c.* FROM cocktail c
    WHERE c.id IN (
        SELECT ct.cocktail_id FROM cocktail_tags ct
        JOIN tag t ON t.id = ct.tag_id
        WHERE t.name IN :tags AND c.user.id = COALESCE(:userId, c.userId)
        GROUP BY ct.cocktail_id
        HAVING COUNT(DISTINCT t.id) = :tagCount
    )
""", nativeQuery = true)
    Page<Cocktail> findByAllTagsOnlyWithUser(
            @Param("tags") List<String> tags,
            int tagCount,
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query(value = """
    SELECT c.* FROM cocktail c
    WHERE c.id IN (
        SELECT ct.cocktail_id FROM cocktail_tags ct
        JOIN tag t ON t.id = ct.tag_id
        WHERE t.name IN :tags
        GROUP BY ct.cocktail_id
        HAVING COUNT(DISTINCT t.id) = :tagCount
    )
""", nativeQuery = true)
    Page<Cocktail> findByAllTagsOnly(
            @Param("tags") List<String> tags,
            int tagCount,
            Pageable pageable
    );

    @Query(value = """
    SELECT c.* FROM cocktail c
    WHERE c.id IN (
        SELECT ci.cocktail_id FROM cocktail_ingredients ci
        JOIN ingredient i ON i.id = ci.ingredient_id
        WHERE i.name IN :ingredients AND c.user.id = COALESCE(:userId, c.user.id))
        GROUP BY ci.cocktail_id
        HAVING COUNT(DISTINCT i.id) = :ingredientCount
    )
""", nativeQuery = true)
    Page<Cocktail> findByAllIngredientsOnlyWithUser(
            @Param("ingredients") List<String> ingredients,
            int ingredientCount,
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query(value = """
    SELECT c.* FROM cocktail c
    WHERE c.id IN (
        SELECT ci.cocktail_id FROM cocktail_ingredients ci
        JOIN ingredient i ON i.id = ci.ingredient_id
        WHERE i.name IN :ingredients)
        GROUP BY ci.cocktail_id
        HAVING COUNT(DISTINCT i.id) = :ingredientCount
    )
""", nativeQuery = true)
    Page<Cocktail> findByAllIngredientsOnly(
            @Param("ingredients") List<String> ingredients,
            int ingredientCount,
            Pageable pageable
    );

    @Query("""
        SELECT DISTINCT c FROM Cocktail c
        LEFT JOIN c.tags t
        LEFT JOIN c.ingredients i
        WHERE (t.tag.name IN :tags OR i.ingredient.name IN :ingredients AND c.user.id = COALESCE(:userId, c.user.id))
        """)
    Page<Cocktail> findByAnyTagsOrAnyIngredientsWithUser(
            @Param("tags") List<String> tags,
            @Param("ingredients") List<String> ingredients,
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query("""
        SELECT DISTINCT c FROM Cocktail c
        LEFT JOIN c.tags t
        LEFT JOIN c.ingredients i
        WHERE (t.tag.name IN :tags OR i.ingredient.name IN :ingredients)
        """)
    Page<Cocktail> findByAnyTagsOrAnyIngredients(
            @Param("tags") List<String> tags,
            @Param("ingredients") List<String> ingredients,
            Pageable pageable
    );

    @Query("""
        SELECT COUNT(DISTINCT c) FROM Cocktail c
        LEFT JOIN c.tags t
        LEFT JOIN c.ingredients i
        WHERE t.tag.name IN :tags OR i.ingredient.name IN :ingredients AND c.user.id = COALESCE(:userId, c.user.id)
        """)
    long countByAnyTagsOrAnyIngredientsWithUser(
            @Param("tags") List<String> tags,
            @Param("ingredients") List<String> ingredients,
            @Param("userId") Long userId
    );

    @Query("""
        SELECT COUNT(DISTINCT c) FROM Cocktail c
        LEFT JOIN c.tags t
        LEFT JOIN c.ingredients i
        WHERE t.tag.name IN :tags OR i.ingredient.name IN :ingredients
        """)
    long countByAnyTagsOrAnyIngredients(
            @Param("tags") List<String> tags,
            @Param("ingredients") List<String> ingredients
    );

    @Query("""
        SELECT COUNT(DISTINCT c) FROM Cocktail c
        JOIN c.tags t
        JOIN c.ingredients i
        WHERE t.tag.name IN :tags AND i.ingredient.name IN :ingredients AND c.user.id = COALESCE(:userId, c.user.id)
        GROUP BY c.id
        HAVING COUNT(DISTINCT t.id) = :tagCount AND COUNT(DISTINCT i.id) = :ingredientCount
        """)
    List<Long> countByAllTagsAndAllIngredientsWithUser(
            @Param("tags") List<String> tags,
            @Param("ingredients") List<String> ingredients,
            @Param("tagCount") long tagCount,
            @Param("ingredientCount") long ingredientCount,
            @Param("userId") Long userId
    );

    @Query("""
        SELECT COUNT(DISTINCT c) FROM Cocktail c
        JOIN c.tags t
        JOIN c.ingredients i
        WHERE t.tag.name IN :tags AND i.ingredient.name IN :ingredients
        GROUP BY c.id
        HAVING COUNT(DISTINCT t.id) = :tagCount AND COUNT(DISTINCT i.id) = :ingredientCount
        """)
    List<Long> countByAllTagsAndAllIngredients(
            @Param("tags") List<String> tags,
            @Param("ingredients") List<String> ingredients,
            @Param("tagCount") long tagCount,
            @Param("ingredientCount") long ingredientCount
    );


    Page<Cocktail> findByNameContaining(String keyword, Pageable pageable);

    int countByNameContaining(String keyword);

    Long countByUser_Id(Long userId);

    void deleteByUser_Id(Long userId);

    Page<Cocktail> findByUser_Id(Long userId, Pageable pageable);

    @Modifying
    @Query("UPDATE Cocktail c SET c.view = c.view + :count WHERE c.id = :id")
    void increaseViews(@Param("id") Long id, @Param("count") long count);
}
