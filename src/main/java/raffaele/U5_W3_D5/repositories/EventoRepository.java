package raffaele.U5_W3_D5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import raffaele.U5_W3_D5.entities.Evento;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento,Long> {
    List<Evento> findByCreatoreId(Long creatoreid);
}
