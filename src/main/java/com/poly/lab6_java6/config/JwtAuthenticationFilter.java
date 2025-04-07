package com.poly.lab6_java6.config;

import com.poly.lab6_java6.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
//chay qua filter nay truoc khi di vao controller xac thuc xem la co ton tai hay kh
    @Override
//    Lấy JWT từ Authorization header.
//Giải mã JWT để lấy username.
//Kiểm tra token hợp lệ, nếu đúng thì đặt user vào SecurityContextHolder.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("request:{}", request.getRequestURL());
        final String authHeader = request.getHeader("Authorization");
        System.err.println("Bearer 0:"+ authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.err.println("Bearer 1:"+ authHeader);
            log.info("........................................");
            try {
//                lay token ngay luc nay
                final String jwt = authHeader.substring(7);
//                lay ra thong tin cua username
                final String username = jwtTokenProvider.extractUsername(jwt);
// kiem tra token co ton tai hay kh
                    CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
                    log.info("userDetails:{}", userDetails);
                    //Kiểm tra token có hết hạn không.
                    if (jwtTokenProvider.validateToken(jwt, userDetails.getUsername())) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
//                                truyen vao ADMIN hoac USER
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                filterChain.doFilter(request, response);
                return;
            } catch (Exception e) {
                // Nếu token hết hạn, xóa authentication để user phải đăng nhập lại
                System.out.println("Token het han hoac sai token");
                SecurityContextHolder.clearContext();
                log.error("failed on set user authentication", e);
            }
        }
//        neu kh có token hoạc di qua api ma cho phep di qua, se vao security config
        System.err.println("request không mang theo token hoạc di qua api ma cho phep di qua, se vao security config");
        filterChain.doFilter(request, response);
    }
}

