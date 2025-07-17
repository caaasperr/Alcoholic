package com.caaasperr.Alcoholic._common.aspect;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic._common.session.model.CustomUserDetails;
import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckOwnerAspect {
    private final CocktailRepository cocktailRepository;

    @Before(
            value = "@annotation(com.caaasperr.Alcoholic._common.annotation.CheckCocktailOwner) && args(cocktailId, .., authentication)",
            argNames = "cocktailId,authentication"
    )
    public void checkOwner(Long cocktailId, Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();

        Cocktail cocktail = cocktailRepository.findById(cocktailId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));

        if (!cocktail.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
    }

}
