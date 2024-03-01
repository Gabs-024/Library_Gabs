package org.library_gabs.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.library_gabs.api.DTO.CredentialsDTO;
import org.library_gabs.api.DTO.TokenDTO;
import org.library_gabs.domain.entity.Users;
import org.library_gabs.exception.InvalidPasswordException;
import org.library_gabs.security.jwt.JwtService;
import org.library_gabs.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Api("Api Usuarios")
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Cadastra um novo usuário.")
    public Users save (@RequestBody @Valid Users users) {
        String CryptoPassword = passwordEncoder.encode(users.getPassword());
        users.setPassword(CryptoPassword);
        return userService.save(users);
    }

    @PostMapping("/auth")
    @ApiOperation("Autentica o novo usuário")
    public TokenDTO authenticate (@RequestBody CredentialsDTO credentials) {
        try {
            Users users = Users.builder()
                    .login(credentials.getLogin())
                    .password(credentials.getPassword())
                    .build();
            UserDetails authenticatedUser = userService.authenticate(users);
            String token = jwtService.generateToken(users);
            return new TokenDTO(users.getLogin(), token);
        } catch (UsernameNotFoundException | InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
