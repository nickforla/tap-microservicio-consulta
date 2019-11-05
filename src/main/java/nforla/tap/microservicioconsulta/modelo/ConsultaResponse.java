package nforla.tap.microservicioconsulta.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaResponse {

    private String cuil;
    private int estado;
    private String mensaje;

}
