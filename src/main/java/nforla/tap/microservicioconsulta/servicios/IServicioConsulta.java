package nforla.tap.microservicioconsulta.servicios;

import nforla.tap.microservicioconsulta.excepciones.CuilNoValidoException;
import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;

import java.util.List;

public interface IServicioConsulta {

    ConsultaResponse analizarEstadoPersona(String cuil) throws CuilNoValidoException;

    List<ConsultaResponse> analizarEstadoPersonas(List<String> cuils);

}
