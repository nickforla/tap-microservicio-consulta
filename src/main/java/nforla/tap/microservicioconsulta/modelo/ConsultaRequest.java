package nforla.tap.microservicioconsulta.modelo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Setter
@Getter
@Document(value = "requests")
public class ConsultaRequest {

    private String username;
    private LocalDateTime horaRequest;
    private int cantidadEstadosSolicitados;

    public ConsultaRequest(String username, LocalDateTime horaRequest, int cantidadEstadosSolicitados) {
        this.username = username;
        this.horaRequest = horaRequest;
        this.cantidadEstadosSolicitados = cantidadEstadosSolicitados;
    }
}
