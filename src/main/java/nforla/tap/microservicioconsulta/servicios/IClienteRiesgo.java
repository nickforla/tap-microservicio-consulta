package nforla.tap.microservicioconsulta.servicios;

import nforla.tap.microservicioconsulta.modelo.Persona;

import java.util.List;

public interface IClienteRiesgo {

    Persona determinarEstadoPersona(Persona persona);
    List<Persona> determinarEstadoPeronas(List<Persona> personas);

}
