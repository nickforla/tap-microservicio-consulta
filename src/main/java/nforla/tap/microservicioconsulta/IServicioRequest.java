package nforla.tap.microservicioconsulta;

import nforla.tap.microservicioconsulta.excepciones.CuotaMaximaRequestsSuperadaException;
import nforla.tap.microservicioconsulta.excepciones.DeterminarEstadoException;
import nforla.tap.microservicioconsulta.modelo.ConsultaRequest;

import java.io.IOException;

public interface IServicioRequest {

    ConsultaRequest doCuotaRequestFilter(String jwtToken, int cantidadEstadosSolicitados) throws IOException, DeterminarEstadoException, CuotaMaximaRequestsSuperadaException;
    void saveRequest(ConsultaRequest request);

}
