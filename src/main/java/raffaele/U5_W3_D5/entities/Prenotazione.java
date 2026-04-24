package raffaele.U5_W3_D5.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@NoArgsConstructor

@ToString
public class Prenotazione {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(nullable = false)
    private LocalDateTime dataPrenotazione;

    public Prenotazione(Utente utente, Evento evento, LocalDateTime dataPrenotazione) {
        this.utente = utente;
        this.evento = evento;
        this.dataPrenotazione = dataPrenotazione;
    }
}