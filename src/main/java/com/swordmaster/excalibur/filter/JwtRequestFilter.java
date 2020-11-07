package com.swordmaster.excalibur.filter;

import com.swordmaster.excalibur.service.SecurityUserService;
import com.swordmaster.excalibur.util.JwtTokenProvider;
import com.swordmaster.excalibur.vo.SecurityUser;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    SecurityUserService securityUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;
        String email = null;

        // jwt 유무 검사 및 토큰 파싱
        if (authorizationHeader != null && !authorizationHeader.equals("")) {
            jwt = authorizationHeader;
            Claims claims = jwtTokenProvider.parseToken(
                        jwt,
                        jwtTokenProvider.makeTokenTypeData(JwtTokenProvider.TOKEN_TYPE.ACCESS_TOKEN)
                    );
            if (claims != null) {
                email = claims.getSubject();
            }
        }

        // 인증 객체 넣기
        if (email != null && (SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            SecurityUser securityUser = (SecurityUser) securityUserService.loadUserByUsername(email);

            if (jwtTokenProvider.validate(jwt, securityUser.getEmail(), JwtTokenProvider.TOKEN_TYPE.ACCESS_TOKEN)) {
                UsernamePasswordAuthenticationToken authToken
                        = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        filterChain.doFilter(request, response);
    }
}
