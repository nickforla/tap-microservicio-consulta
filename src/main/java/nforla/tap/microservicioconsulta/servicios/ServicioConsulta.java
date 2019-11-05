package nforla.tap.microservicioconsulta.servicios;

import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;
import nforla.tap.microservicioconsulta.repositories.PersonaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioConsulta implements IServicioConsulta {

    private PersonaRepository personaRepository;

    public ServicioConsulta(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public ConsultaResponse analizarEstadoPersona(String cuil) {
        return null;
    }

    @Override
    public List<ConsultaResponse> analizarEstadoPersonas(List<String> cuils) {
        return null;
    }
}
