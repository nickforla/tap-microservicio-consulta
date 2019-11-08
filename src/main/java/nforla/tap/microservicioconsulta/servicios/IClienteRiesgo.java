package nforla.tap.microservicioconsulta.servicios;

import nforla.tap.microservicioconsulta.excepciones.DeterminarEstadoException;
import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;
import nforla.tap.microservicioconsulta.modelo.Persona;

import java.io.IOException;
import java.util.List;

public interface IClienteRiesgo {

    ConsultaResponse determinarEstadoPersona(Persona persona) throws IOException, DeterminarEstadoException;
    List<ConsultaResponse> determinarEstadoPeronas(List<Persona> personas) throws IOException, DeterminarEstadoException;

}
