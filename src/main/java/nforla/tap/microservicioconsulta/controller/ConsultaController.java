package nforla.tap.microservicioconsulta.controller;

import nforla.tap.microservicioconsulta.excepciones.CuilNoValidoException;
import nforla.tap.microservicioconsulta.excepciones.DeterminarEstadoException;
import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;
import nforla.tap.microservicioconsulta.servicios.IServicioConsulta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/analizarEstado")
public class ConsultaController {

    private IServicioConsulta servicioConsulta;
    private final Logger logger = LoggerFactory.getLogger(ConsultaController.class);

    public ConsultaController(IServicioConsulta servicioConsulta) {
        this.servicioConsulta = servicioConsulta;
    }

    @GetMapping(path = "/persona/{cuil}")
    public ResponseEntity<ConsultaResponse> analizarEstadoPersona(@PathVariable String cuil){

        try{

            ConsultaResponse consultaResponse = servicioConsulta.analizarEstadoPersona(cuil);

            return ResponseEntity.ok(consultaResponse);

        }catch (CuilNoValidoException exc){

            logger.error(exc.getMessage());

            return ResponseEntity.badRequest()
                    .body(new ConsultaResponse(cuil, exc.getMessage()));

        }catch (IOException | DeterminarEstadoException exc){

            logger.error("Ha ocurrido una excepción: " + exc.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ConsultaResponse(cuil, "Ha ocurrido un error al intentar determinar el estado. Intente nuevamente."));

        }
    }

    @GetMapping(path = "/personas")
    public ResponseEntity analizarEstadoPersonas(@RequestBody List<String> cuils){

        logger.info(cuils.toString());
        try{

            List<ConsultaResponse> responses = servicioConsulta.analizarEstadoPersonas(cuils);

            return ResponseEntity.ok(responses);

        }catch (IOException | DeterminarEstadoException exc){

            logger.error("Ha ocurrido una excepción: " + exc.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ConsultaResponse("Ha ocurrido un error al intentar determinar el estado. Intente nuevamente."));

        }
    }
}
