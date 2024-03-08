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
        log.debug("Token requested for user : " + dto.getEmail());
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(dto.getEmail(), dto.getPassword());
        String token = tokenService.generateToken(authenticationRequest);
        log.info("Token granted : " + token + "  -  For user : " + dto.getEmail());

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @GetMapping("/home")
    public ResponseEntity<TextResponseDTO> homeSweetHome(){
        return ResponseEntity.ok(new TextResponseDTO("Hi home !"));
    }


    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> getUser(@RequestHeader("Authorization") String requestTokenHeader) {
      String username = tokenService.decodeTokenUsername(requestTokenHeader);

      User uer = userService.findByEmail(username);
      UserDTO response = userMapper.modelToDto(uer);

      return ResponseEntity.ok(userMapper.dtoToUserInfoDto(response));

    }

    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody RegistrationDTO dto){
        User createdUser = userService.registerUser(dto);

        return this.token(new LoginDTO(createdUser.getEmail(), createdUser.getPassword()));
    }


}
