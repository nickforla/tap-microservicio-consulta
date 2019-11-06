package nforla.tap.microservicioconsulta.servicios;

import nforla.tap.microservicioconsulta.excepciones.CuilNoValidoException;
import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;
import nforla.tap.microservicioconsulta.modelo.Persona;
import nforla.tap.microservicioconsulta.repositorios.PersonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioConsulta implements IServicioConsulta {

    private PersonaRepository personaRepository;
    private IClienteRiesgo clienteRiesgo;
    private final Logger logger = LoggerFactory.getLogger(ServicioConsulta.class);

    public ServicioConsulta(PersonaRepository personaRepository, IClienteRiesgo clienteRiesgo) {
        this.personaRepository = personaRepository;
        this.clienteRiesgo = clienteRiesgo;
    }

    @Override
    public ConsultaResponse analizarEstadoPersona(String cuil) throws CuilNoValidoException{

        if(ValidadorCuil.esCuilValido(cuil)){

            Optional<Persona> personaOptional = personaRepository.findByCuil(cuil);

            if(personaOptional.isPresent()){

                try{
                    return clienteRiesgo.determinarEstadoPersona(personaOptional.get());
                }catch (IOException exc){

                    logger.error(exc.getMessage());
                    throw new CuilNoValidoException("LALALA");
                }

            }

            return new ConsultaResponse(cuil, 0, "No se encontraron datos del solicitante para determinar el estado");

        }

        throw new CuilNoValidoException("El cuil recibido no es válido");

    }

    @Override
    public List<ConsultaResponse> analizarEstadoPersonas(List<String> cuils) {
        return null;
    }
}
