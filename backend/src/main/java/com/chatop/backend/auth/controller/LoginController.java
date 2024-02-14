package com.chatop.backend.auth.controller;

import com.chatop.backend.auth.service.TokenService;
import com.chatop.backend.dtos.*;
import com.chatop.backend.mappers.UserMapper;
import com.chatop.backend.models.User;
import com.chatop.backend.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("auth")
public class LoginController {

    private final TokenService tokenService;

    private final UserService userService;

    private final UserMapper userMapper;



    public LoginController(TokenService tokenService, UserService userService, UserMapper userMapper) {
        this.tokenService = tokenService;
        this.userService = userService;
      this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> token(@RequestBody LoginDTO dto) {
        log.debug("Token requested for user : " + dto.getLogin());
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(dto.getLogin(), dto.getPassword());
        String token = tokenService.generateToken(authenticationRequest);
        log.info("Token granted : " + token + "for user : " + dto.getLogin());

        return ResponseEntity.ok(new TokenDTO(token));

    }

    @GetMapping("/home")
    public ResponseEntity<TextResponseDTO> homeSweetHome(){
        return ResponseEntity.ok(new TextResponseDTO("Hi home !"));
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER', 'SCOPE_ROLE_ADMIN', 'SCOPE_OAUTH2_USER')")
    @GetMapping("/me")
    public ResponseEntity<TextResponseDTO> getUser(@RequestHeader("Authorization") String requestTokenHeader) {
        log.info("User");
        return ResponseEntity.ok(new TextResponseDTO("Hi " + tokenService.decodeTokenUsername(requestTokenHeader)));

    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegistrationDTO dto){
        User createdUser = userService.registerUser(dto);
        return ResponseEntity.ok(userMapper.modelToDto(createdUser) );
    }


}
