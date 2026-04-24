package raffaele.U5_W3_D5.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import raffaele.U5_W3_D5.entities.Utente;
import raffaele.U5_W3_D5.exeptions.UnauthorizedException;
import raffaele.U5_W3_D5.service.UtenteService;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final TokenTools tokenTools;
    private final UtenteService utenteService;

    public TokenFilter(TokenTools tokenTools, UtenteService utenteService) {
        this.tokenTools = tokenTools;
        this.utenteService = utenteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Inserire il token nell'authorization header nel formato corretto");
        }

        String accessToken = authHeader.replace("Bearer ", "");

        tokenTools.verifyToken(accessToken);

        Long utenteId = tokenTools.extractIdFromToken(accessToken);
        Utente utente = utenteService.findById(utenteId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                utente,
                null,
                utente.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}