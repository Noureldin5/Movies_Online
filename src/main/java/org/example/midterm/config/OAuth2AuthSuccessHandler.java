package org.example.midterm.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.midterm.dto.auth.AuthResponse;
import org.example.midterm.entities.RefreshToken;
import org.example.midterm.entities.User;
import org.example.midterm.service.RefreshTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final org.example.midterm.service.UserService userService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                      Authentication authentication) throws IOException, ServletException {
        
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauthToken.getPrincipal();
        
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        
        // Find or create user
        User user = userService.findOrCreateOAuth2User(email, name, oauthToken.getAuthorizedClientRegistrationId());
        
        // Generate JWT tokens
        String accessToken = JwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        
        // Return the tokens in the response
        response.setContentType("application/json");
        response.getWriter().write(
            new ObjectMapper().writeValueAsString(
                new AuthResponse(accessToken, refreshToken.getToken())
            )
        );
    }
}