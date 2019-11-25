package nforla.tap.microservicioconsulta.modelo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "personas")
public class Persona {

    private String cuil;
    private List<Deuda> deudas;

    public Persona(String cuil){
        this.cuil = cuil;
    }
}
