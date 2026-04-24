package raffaele.U5_W3_D5.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Entity
@Table(name="eventi")
@Getter
@Setter
@NoArgsConstructor

@ToString
public class Evento {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String titolo;
    @Column(nullable = false)
    private String descrizione;
    @Column(nullable = false)
    private String luogo;
    @Column(nullable = false)
    private int maxposti;
    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne
    @JoinColumn( name="creatore",nullable = false)
    private Utente creatore;

    public Evento(String titolo, String descrizione, String luogo, int maxposti, LocalDate data, Utente creatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.luogo = luogo;
        this.maxposti = maxposti;
        this.data = data;
        this.creatore = creatore;
    }
}
