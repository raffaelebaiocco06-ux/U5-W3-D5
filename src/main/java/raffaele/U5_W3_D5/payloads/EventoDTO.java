package raffaele.U5_W3_D5.payloads;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record EventoDTO(

        @NotBlank(message = "Il titolo è obbligatorio")
        String titolo,

        @NotBlank(message = "La descrizione è obbligatoria")
        String descrizione,

        @NotBlank(message = "Il luogo è obbligatorio")
        String luogo,

        @Min(value = 20, message = "I posti devono essere almeno 1")
        int maxposti,

        @NotNull(message = "La data è obbligatoria")
        @FutureOrPresent(message = "La data non può essere nel passato")
        LocalDate data


) {

}
