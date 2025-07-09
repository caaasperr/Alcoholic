package com.caaasperr.Alcoholic.domain.user.controller;

import com.caaasperr.Alcoholic.domain.user.dto.GetUserResponse;
import com.caaasperr.Alcoholic.domain.user.dto.RegisterRequest;
import com.caaasperr.Alcoholic.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController implements UserApi {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest request
    ) {
        userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<GetUserResponse> getUser(
            @PathVariable String username
    ) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
