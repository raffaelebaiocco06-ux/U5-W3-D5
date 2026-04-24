package raffaele.U5_W3_D5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import raffaele.U5_W3_D5.entities.Evento;
import raffaele.U5_W3_D5.entities.Prenotazione;
import raffaele.U5_W3_D5.entities.Utente;

import java.util.List;
import java.util.Optional;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByUtente(Utente utente);
    boolean existsByUtenteAndEvento(Utente utente, Evento evento);
    Optional<Prenotazione> findByUtenteAndEvento(Utente utente, Evento evento);
    int countByEvento(Evento evento);
}