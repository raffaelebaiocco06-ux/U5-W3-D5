package raffaele.U5_W3_D5.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import raffaele.U5_W3_D5.entities.Prenotazione;
import raffaele.U5_W3_D5.entities.Utente;
import raffaele.U5_W3_D5.service.PrenotazioneService;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @PostMapping("/eventi/{eventoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione prenota(@PathVariable Long eventoId, Authentication authentication) {
        Utente utenteLoggato = (Utente) authentication.getPrincipal();
        return this.prenotazioneService.prenotaEvento(eventoId, utenteLoggato);
    }

    @GetMapping("/mio")
    public List<Prenotazione> getmie(Authentication authentication) {
        Utente utenteLoggato = (Utente) authentication.getPrincipal();
        return this.prenotazioneService.getPrenotazioniUtente(utenteLoggato);
    }

    @DeleteMapping("/eventi/{eventoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancellaprenotazione(@PathVariable Long eventoId, Authentication authentication) {
        Utente utenteLoggato = (Utente) authentication.getPrincipal();
        this.prenotazioneService.annullaPrenotazione(eventoId, utenteLoggato);
    }
}
