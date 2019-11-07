package nforla.tap.microservicioconsulta.modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConsultaResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cuil;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int estado;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mensaje;

    public ConsultaResponse(String cuil, int estado){
        this.cuil = cuil;
        this.estado = estado;
    }

    public ConsultaResponse(String cuil, String mensaje){
        this.cuil = cuil;
        this.mensaje = mensaje;
    }

    public ConsultaResponse(String mensaje){
        this.mensaje = mensaje;
    }
}
