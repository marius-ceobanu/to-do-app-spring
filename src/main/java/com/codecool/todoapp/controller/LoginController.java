package com.codecool.todoapp.controller;

import com.codecool.todoapp.entity.UserLoginResponse;
import com.codecool.todoapp.security.JwtTokenServices;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "")
@AllArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenServices jwtTokenServices;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestParam("username") String usrname, @RequestParam("password") String psw) {
        try {
            String username = usrname;

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, psw)
            );

            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            String token = jwtTokenServices.createToken(username, roles);

            UserLoginResponse response = UserLoginResponse.builder()
                                            .username(username)
                                            .roles(roles)
                                            .token(token)
                                            .build();

            System.out.println(response.toString());

            return ResponseEntity.ok(response);

        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
