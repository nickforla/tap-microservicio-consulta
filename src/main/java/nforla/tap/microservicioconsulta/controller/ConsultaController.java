package nforla.tap.microservicioconsulta.controller;

import nforla.tap.microservicioconsulta.excepciones.CuilNoValidoException;
import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;
import nforla.tap.microservicioconsulta.servicios.IServicioConsulta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                    .body(new ConsultaResponse(cuil, 0, exc.getMessage()));

        }
    }
}
