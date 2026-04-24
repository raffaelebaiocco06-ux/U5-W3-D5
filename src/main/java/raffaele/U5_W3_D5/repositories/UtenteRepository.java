package raffaele.U5_W3_D5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import raffaele.U5_W3_D5.entities.Utente;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByEmail(String email);

    boolean existsByEmail(String email);
}