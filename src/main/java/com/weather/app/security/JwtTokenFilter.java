package com.weather.app.security;

import com.weather.app.service.WeatherService;
import com.weather.app.utils.Constants;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final WeatherService weatherService;

    public JwtTokenFilter(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (!header.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String authorizationHeader = Objects.requireNonNull(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
        final String jwt = authorizationHeader.replace("Bearer ", "");
        if (!isJWTValid(jwt)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if (Objects.equals(httpServletRequest.getServletPath().split("/")[1], "root") &&
                !Objects.equals(getUserRole(jwt), "ROOT")){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }




        UserDetails user = weatherService
                .loadUserByUsername(getUserId(jwt));

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                user, null,
                user == null ?
                        List.of() : user.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean isJWTValid(String jwt) {
        boolean returnValue = true;

        try {
            String subject = Jwts.parser()
                    .setSigningKey(Constants.ACCESS_KEY)
                    .parseClaimsJws(jwt)
                    .getBody()
                    .toString();


            if(subject == null || subject.isEmpty()) {
                returnValue = false;
            }
        }catch (Exception ex){
            returnValue = false;
        }
        return returnValue;
    }

    public String getUserId(String jwt){
        return Jwts.parser()
                .setSigningKey(Constants.ACCESS_KEY)
                .parseClaimsJws(jwt)
                .getBody()
                .get("id")
                .toString();
    }

    public String getUserRole(String jwt){
        return Jwts.parser()
                .setSigningKey(Constants.ACCESS_KEY)
                .parseClaimsJws(jwt)
                .getBody()
                .get("userRole")
                .toString();
    }
}
