package com.caaasperr.Alcoholic.domain.user.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.user.dto.GetUserResponse;
import com.caaasperr.Alcoholic.domain.user.dto.RegisterRequest;
import com.caaasperr.Alcoholic.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {
        userRepository.save(request.toUser(passwordEncoder));
    }

    public GetUserResponse getUser(String username) {
        return GetUserResponse.of(userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
