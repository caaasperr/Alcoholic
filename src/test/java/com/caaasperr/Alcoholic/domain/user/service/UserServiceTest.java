package com.caaasperr.Alcoholic.domain.user.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.user.dto.GetUserResponse;
import com.caaasperr.Alcoholic.domain.user.dto.RegisterRequest;
import com.caaasperr.Alcoholic.domain.user.model.User;
import com.caaasperr.Alcoholic.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void register_정상동작() {
        // given
        RegisterRequest request = mock(RegisterRequest.class);
        User user = new User();
        when(request.toUser(passwordEncoder)).thenReturn(user);

        // when
        userService.register(request);

        // then
        verify(userRepository).save(user);
    }

    @Test
    void getUser_존재할때_정상반환() {
        // given
        String username = "postman";
        User user = new User();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // when
        GetUserResponse result = userService.getUser(username);

        // then
        assertNotNull(result);
    }

    @Test
    void getUser_존재하지않을때_예외발생() {
        // given
        String username = "unknown";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // when & then
        CustomException ex = assertThrows(CustomException.class, () -> userService.getUser(username));
        assertEquals(ErrorCode.NOT_FOUND_USER, ex.getErrorCode());
    }

    @Test
    void deleteUser_정상동작() {
        // given
        Long id = 1L;

        // when
        userService.deleteUser(id);

        // then
        verify(userRepository).deleteById(id);
    }
}