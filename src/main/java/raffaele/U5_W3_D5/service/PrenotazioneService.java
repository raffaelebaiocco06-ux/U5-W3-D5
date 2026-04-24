package raffaele.U5_W3_D5.service;

import org.springframework.stereotype.Service;
import raffaele.U5_W3_D5.entities.Evento;
import raffaele.U5_W3_D5.entities.Prenotazione;
import raffaele.U5_W3_D5.entities.Utente;
import raffaele.U5_W3_D5.exeptions.BadRequestExeption;
import raffaele.U5_W3_D5.exeptions.NotFoundExeption;
import raffaele.U5_W3_D5.repositories.PrenotazioneRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoService eventoService;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, EventoService eventoService) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.eventoService = eventoService;
    }

    public Prenotazione prenotaEvento(Long eventoId, Utente utente) {
        Evento evento = this.eventoService.findById(eventoId);
        if (this.prenotazioneRepository.existsByUtenteAndEvento(utente, evento)) {
            throw new BadRequestExeption("Hai già prenotato questo evento");
        }

        int prenotazionifatte = this.prenotazioneRepository.countByEvento(evento);

        if (prenotazionifatte >= evento.getMaxposti()) {
            throw new BadRequestExeption("Posti esauriti per questo evento");
        }
        Prenotazione prenotazione = new Prenotazione(utente,evento,LocalDateTime.now());
        return this.prenotazioneRepository.save(prenotazione);
    }

    public List<Prenotazione> getPrenotazioniUtente(Utente utente) {
        return this.prenotazioneRepository.findByUtente(utente);
    }

    public void annullaPrenotazione(Long eventoId, Utente utente) {
        Evento evento = this.eventoService.findById(eventoId);
        Prenotazione prenotazione = this.prenotazioneRepository.findByUtenteAndEvento(utente, evento).orElseThrow(() -> new NotFoundExeption("Prenotazione non trovata"));
        this.prenotazioneRepository.delete(prenotazione);
    }
}
