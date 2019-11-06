package nforla.tap.microservicioconsulta.modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mensaje;

    public ConsultaResponse(String cuil, int estado){
        this.cuil = cuil;
        this.estado = estado;
    }
}
