package raffaele.U5_W3_D5.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import raffaele.U5_W3_D5.entities.Utente;
import raffaele.U5_W3_D5.exeptions.NotFoundExeption;
import raffaele.U5_W3_D5.exeptions.UnauthorizedException;
import raffaele.U5_W3_D5.payloads.LoginDTO;
import raffaele.U5_W3_D5.security.TokenTools;

@Service
public class AuthService {
    private final UtenteService utenteService;
    private final TokenTools tokenTools;
    private final PasswordEncoder bcpypt;

    public AuthService(UtenteService utenteService, TokenTools tokenTools, PasswordEncoder bcpypt) {
        this.utenteService = utenteService;
        this.tokenTools = tokenTools;
        this.bcpypt = bcpypt;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        Utente utente = this.utenteService.findByEmail(body.email());

        if (bcpypt.matches(body.password(), utente.getPassword())) {
            return tokenTools.generateToken(utente);
        } else {
            throw new UnauthorizedException("Credenziali non valide");
        }
    }
}
