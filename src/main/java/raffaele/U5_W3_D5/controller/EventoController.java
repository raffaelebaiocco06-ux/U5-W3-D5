package raffaele.U5_W3_D5.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import raffaele.U5_W3_D5.entities.Evento;
import raffaele.U5_W3_D5.entities.Utente;
import raffaele.U5_W3_D5.payloads.EventoDTO;
import raffaele.U5_W3_D5.service.EventoService;

import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<Evento> getAllEventi() {
        return this.eventoService.findAll();
    }

    @GetMapping("/{id}")
    public Evento getEventoById(@PathVariable Long id) {
        return this.eventoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento createEvento(@RequestBody @Valid EventoDTO body, Authentication authentication) {
        Utente utentel = (Utente) authentication.getPrincipal();
        return this.eventoService.save(body, utentel);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento updateEvento(@PathVariable Long id, @RequestBody @Valid EventoDTO body, Authentication authentication) {
        Utente utentel = (Utente) authentication.getPrincipal();
        return this.eventoService.findByIdAndUpdate(id, body, utentel);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public void deleteEvento(@PathVariable Long id, Authentication authentication) {
        Utente utentel = (Utente) authentication.getPrincipal();
        this.eventoService.findByIdAndDelete(id, utentel);
    }
}
