package org.yujin.myproc.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yujin.myproc.entity.User;
import org.yujin.myproc.repository.UserRepository;
import org.yujin.myproc.util.JwtUtil;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //헤더에 "Authorization" 에 "Bearer token" 형태로 등록된 부분 꺼내오기
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("authorization : {}", authorization);

        //token 존재 여부 체크
        if(authorization == null || !authorization.startsWith("Bearer ")) {

            log.error("no authorization");

            filterChain.doFilter(request, response);
            return;
        }

        //token 뽑아오기
        String token = authorization.split(" ")[1];

        //token 만료 여부 체크
        try {
            JwtUtil.isExpired(token, secretKey);
        } catch (Exception e ){

            log.error("token expired");

            filterChain.doFilter(request, response);
            return;
        }

        //token 은 email 정보를 통해 암호화 되었다고 하자
        String email = JwtUtil.getEmail(token, secretKey);

        User existUser = userRepository.findByEmail(email);

        log.info("JWT existUser : {}", existUser);

        //필터 적용
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority(existUser.getRole())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
