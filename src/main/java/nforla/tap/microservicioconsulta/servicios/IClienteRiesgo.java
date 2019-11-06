package nforla.tap.microservicioconsulta.servicios;

import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;
import nforla.tap.microservicioconsulta.modelo.Persona;

import java.util.List;

public interface IClienteRiesgo {

    ConsultaResponse determinarEstadoPersona(Persona persona);
    List<ConsultaResponse> determinarEstadoPeronas(List<Persona> personas);

}
