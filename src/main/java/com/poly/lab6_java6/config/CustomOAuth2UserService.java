package com.poly.lab6_java6.config;

import com.poly.lab6_java6.models.Authority;
import com.poly.lab6_java6.models.Role;
import com.poly.lab6_java6.models.User;
import com.poly.lab6_java6.repositories.AuthorityRepository;
import com.poly.lab6_java6.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // Lấy email từ Google
        String email = oAuth2User.getAttribute("email");
        System.out.println(email);
        String picture = oAuth2User.getAttribute("picture");
        String fullname = oAuth2User.getAttribute("name");
        // Kiểm tra xem user có trong database không
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            System.out.println("user null");
            User userRegister = new User(email, fullname,Long.toHexString(System.currentTimeMillis()), picture,null);
            userRegister.setPassword(new BCryptPasswordEncoder().encode(userRegister.getPassword()));
            userRegister = userRepository.save(userRegister);
            Authority auth = new Authority();
            auth.setUser(userRegister);
            auth.setRole(new Role("USER"));
            authorityRepository.save(auth);
        }else{
            System.out.println("user ko null");
            user.get().setFullname(fullname);
            user.get().setPicture(picture);
            userRepository.save(user.get());
        }
        // Lấy role từ database
        List<Authority> authorities = authorityRepository.findAllByEmail(email);
        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<GrantedAuthority>();
        for (Authority authority : authorities) {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(authority.getRole().getRole()));
        }
        System.out.println(authorities.get(0).getRole().getRole());
        // Tạo UsernamePasswordAuthenticationToken để đồng bộ với đăng nhập Username/Password
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                email,  // Username
                null,  // Không có password vì dùng OAuth2
                grantedAuthoritySet  // Quyền từ database
        );
        // Set authentication vào SecurityContext để sử dụng chung
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Trả về OAuth2User (Spring yêu cầu trả về OAuth2User)
        return new DefaultOAuth2User(grantedAuthoritySet, oAuth2User.getAttributes(), "email");
    }
}
