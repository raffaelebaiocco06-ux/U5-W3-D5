package raffaele.U5_W3_D5.service;

import org.springframework.stereotype.Service;
import raffaele.U5_W3_D5.entities.Evento;
import raffaele.U5_W3_D5.entities.Utente;
import raffaele.U5_W3_D5.exeptions.BadRequestExeption;
import raffaele.U5_W3_D5.exeptions.NotFoundExeption;
import raffaele.U5_W3_D5.payloads.EventoDTO;
import raffaele.U5_W3_D5.repositories.EventoRepository;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }


public Evento save(EventoDTO body, Utente creatore) {
    Evento evento = new Evento(body.titolo(),body.descrizione(),body.luogo(),body.maxposti(),body.data(),creatore);

    return this.eventoRepository.save(evento);
}

public List<Evento> findAll() {
    return this.eventoRepository.findAll();
}

public Evento findById(Long id) {
    return this.eventoRepository.findById(id)
            .orElseThrow(() -> new NotFoundExeption("Evento con id " + id + " non trovato"));
}

public Evento findByIdAndUpdate(Long id, EventoDTO body, Utente utenteLoggato) {
    Evento found = this.findById(id);

    if (!found.getCreatore().getId().equals(utenteLoggato.getId())) {
        throw new BadRequestExeption("Puoi modificare solo gli eventi creati da te");
    }

    found.setTitolo(body.titolo());
    found.setDescrizione(body.descrizione());
    found.setLuogo(body.luogo());
    found.setMaxposti(body.maxposti());
    found.setData(body.data());

    return this.eventoRepository.save(found);
}

public void findByIdAndDelete(Long id, Utente utenteLoggato) {
    Evento found = this.findById(id);

    if (!found.getCreatore().getId().equals(utenteLoggato.getId())) {
        throw new BadRequestExeption("Puoi eliminare solo gli eventi creati da te");
    }

    this.eventoRepository.delete(found);
}
}
