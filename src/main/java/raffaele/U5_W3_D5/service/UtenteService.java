package raffaele.U5_W3_D5.service;

import org.springframework.stereotype.Service;
import raffaele.U5_W3_D5.entities.Utente;
import raffaele.U5_W3_D5.exeptions.BadRequestExeption;
import raffaele.U5_W3_D5.exeptions.NotFoundExeption;
import raffaele.U5_W3_D5.payloads.UtenteDTO;
import raffaele.U5_W3_D5.repositories.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder bcrypt;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder bcrypt) {
        this.utenteRepository = utenteRepository;
        this.bcrypt = bcrypt;
    }

    public Utente save(UtenteDTO body) {
        if (this.utenteRepository.existsByEmail(body.email())) {
            throw new BadRequestExeption("L'indirizzo email " + body.email() + " è già in uso!");
        }

        Utente nuovoUtente = new Utente(body.nome(),body.cognome(),body.email(),this.bcrypt.encode(body.password()),body.ruolo());

        return this.utenteRepository.save(nuovoUtente);
    }

    public Utente findById(Long id) {
        return this.utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundExeption("Utente con id " + id + " non trovato"));
    }

    public Utente findByIdAndUpdate(Long id, UtenteDTO body) {
        Utente found = this.findById(id);

        if (!found.getEmail().equals(body.email())) {
            if (this.utenteRepository.existsByEmail(body.email())) {
                throw new BadRequestExeption("L'indirizzo email " + body.email() + " è già in uso!");
            }
        }

        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setEmail(body.email());
        found.setPassword(this.bcrypt.encode(body.password()));
        found.setRuolo(body.ruolo());

        Utente updatedUtente = this.utenteRepository.save(found);

        System.out.println("L'utente con id " + updatedUtente.getId() + " è stato modificato correttamente!");

        return updatedUtente;
    }

    public void findByIdAndDelete(Long id) {
        Utente found = this.findById(id);
        this.utenteRepository.delete(found);
    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundExeption("L'utente con email " + email + " non è stato trovato!"));
    }
}
