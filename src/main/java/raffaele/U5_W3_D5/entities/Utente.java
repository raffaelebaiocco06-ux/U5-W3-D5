package raffaele.U5_W3_D5.entities;


import jakarta.persistence.*;
import lombok.*;
import raffaele.U5_W3_D5.Ruolo;

@Entity
@Table(name="utenti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Utente {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ruolo ruolo;

}
