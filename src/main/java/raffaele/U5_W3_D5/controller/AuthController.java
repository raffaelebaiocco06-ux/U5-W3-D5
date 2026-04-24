package raffaele.U5_W3_D5.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raffaele.U5_W3_D5.entities.Utente;
import raffaele.U5_W3_D5.exeptions.BadRequestExeption;
import raffaele.U5_W3_D5.payloads.LoginDTO;
import raffaele.U5_W3_D5.payloads.LoginRespDTO;
import raffaele.U5_W3_D5.payloads.NewUtenteRespDTO;
import raffaele.U5_W3_D5.payloads.UtenteDTO;
import raffaele.U5_W3_D5.service.AuthService;
import raffaele.U5_W3_D5.service.UtenteService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UtenteService utenteService;

    public AuthController(AuthService authService, UtenteService utenteService) {
        this.authService = authService;
        this.utenteService = utenteService;
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        return new LoginRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public NewUtenteRespDTO saveUser(@RequestBody @Validated UtenteDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new BadRequestExeption(errors.toString());
        }

        Utente utente = this.utenteService.save(body);
        return new NewUtenteRespDTO(utente.getId());
    }
}
