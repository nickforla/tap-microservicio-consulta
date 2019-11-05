package nforla.tap.microservicioconsulta.modelo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(value = "personas")
public class Persona {

    private String cuil;
    private List<Deuda> deudas;

}
