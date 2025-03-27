package com.poly.lab6_java6.controllers;

import com.poly.lab6_java6.config.CustomUserDetails;
import com.poly.lab6_java6.config.JwtTokenProvider;
import com.poly.lab6_java6.models.LoginRequest;
import com.poly.lab6_java6.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginContext;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try{
//            xác thực thông tin của user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            System.err.println("controler 1");

//            nếu đúng tạo Authentication và set với SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(customUserDetails.getUsername());
            return ResponseEntity.ok(Map.of(
                    "message", "Đăng nhập thành công!",
                    "token", jwt
            ));
        }catch (UsernameNotFoundException | BadCredentialsException e){
            return  new ResponseEntity<>("Sai thông tin đăng nhập",HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/oauth2/success")
    public ResponseEntity<?> getUserInfo(OAuth2AuthenticationToken authentication) {
        System.out.println("Authentication: " + authentication); // Debug
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
        OAuth2User user = authentication.getPrincipal();
        return ResponseEntity.ok(user.getAttributes());
    }
    @GetMapping("/oauth2/failure")
    public ResponseEntity<String> loginFailed() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Đăng nhập thất bại!");
    }

    @GetMapping("/ok")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok("okkk");
    }
}
