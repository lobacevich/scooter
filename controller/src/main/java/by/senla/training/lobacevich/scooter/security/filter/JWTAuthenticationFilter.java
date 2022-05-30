package by.senla.training.lobacevich.scooter.security.filter;

import by.senla.training.lobacevich.scooter.security.JWTTokenProvider;
import by.senla.training.lobacevich.scooter.service.impl.UserDetailsServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    public static final Logger LOG = LogManager.getLogger();
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private UserDetailsServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJWTFromRequest(request);
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsernameFromToken(token);
                UserDetails user = userService.loadUserByUsername(username);
                RememberMeAuthenticationToken authToken = new RememberMeAuthenticationToken(
                        token, user, null);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            LOG.error("Could not set user authentication");
            LOG.error(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
            return token.split(" ")[1];
        } else {
            return null;
        }
    }
}