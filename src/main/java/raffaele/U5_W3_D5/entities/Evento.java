package raffaele.U5_W3_D5.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Entity
@Table(name="eventi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
