package nforla.tap.microservicioconsulta.servicios;

import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;
import nforla.tap.microservicioconsulta.modelo.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteRiesgo implements IClienteRiesgo {

    private final Logger logger = LoggerFactory.getLogger(ClienteRiesgo.class);

    @Override
    public ConsultaResponse determinarEstadoPersona(Persona persona) {
        logger.info("CONSULTADO AL MS DE RISGO");
        return new ConsultaResponse(persona.getCuil(), 1);
    }

    @Override
    public List<ConsultaResponse> determinarEstadoPeronas(List<Persona> personas) {
        return null;
    }
}
