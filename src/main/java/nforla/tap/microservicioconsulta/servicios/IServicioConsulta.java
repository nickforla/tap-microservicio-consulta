package nforla.tap.microservicioconsulta.servicios;

import nforla.tap.microservicioconsulta.excepciones.CuilNoValidoException;
import nforla.tap.microservicioconsulta.excepciones.DeterminarEstadoException;
import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;

import java.io.IOException;
import java.util.List;

public interface IServicioConsulta {

    ConsultaResponse analizarEstadoPersona(String cuil) throws CuilNoValidoException, IOException, DeterminarEstadoException;

    List<ConsultaResponse> analizarEstadoPersonas(List<String> cuils) throws IOException,DeterminarEstadoException;

}
