package nforla.tap.microservicioconsulta.servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import nforla.tap.microservicioconsulta.TimeStampProvider;
import nforla.tap.microservicioconsulta.excepciones.CuotaMaximaRequestsSuperadaException;
import nforla.tap.microservicioconsulta.excepciones.DeterminarEstadoException;
import nforla.tap.microservicioconsulta.modelo.ConsultaRequest;
import nforla.tap.microservicioconsulta.repositorios.ConsultaRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class ServicioRequest implements IServicioRequest {

    private final Logger logger = LoggerFactory.getLogger(ServicioRequest.class);
    private ConsultaRequestRepository consultaRequestRepository;
    private ObjectMapper objectMapper;
    private TimeStampProvider timeStampProvider;

    public ServicioRequest(ConsultaRequestRepository consultaRequestRepository, ObjectMapper objectMapper, TimeStampProvider timeStampProvider) {
        this.consultaRequestRepository = consultaRequestRepository;
        this.objectMapper = objectMapper;
        this.timeStampProvider = timeStampProvider;
    }

    public ConsultaRequest doCuotaRequestFilter(String jwtToken, int cantidadEstadosSolicitados) throws IOException, DeterminarEstadoException, CuotaMaximaRequestsSuperadaException {

        if(jwtToken == null || jwtToken.equals("")){

            throw new DeterminarEstadoException("JWT no está presente en el header del request");
        }

        Map<String, Object> jwtPayload = getJwtPayload(jwtToken.replace("Bearer ", ""));

        String username = (String)jwtPayload.get("sub");
        int cuotaMaximaRequestsPorHora = (int) jwtPayload.get("cta");

        Stream<ConsultaRequest> consultasEnUltimaHora = consultaRequestRepository.streamByUsernameAndHoraRequestBetween(username, timeStampProvider.getTimeStamp().minusHours(1), timeStampProvider.getTimeStamp());

        int estadosSolicitadosUltimaHora = consultasEnUltimaHora.mapToInt(ConsultaRequest::getCantidadEstadosSolicitados).sum();

        if(estadosSolicitadosUltimaHora + cantidadEstadosSolicitados > cuotaMaximaRequestsPorHora){
            throw new CuotaMaximaRequestsSuperadaException(String.format("La consulta supera la cuota máxima(%d) de solicitudes del usuario(%s)!", cuotaMaximaRequestsPorHora, username));
        }

        return new ConsultaRequest(username, LocalDateTime.now(), cantidadEstadosSolicitados);

    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getJwtPayload(String jwtToken) throws IOException{

        String payload = jwtToken.split("\\.")[1];

        return objectMapper.readValue(Base64Utils.decodeFromString(payload), Map.class);

    }

    public void saveRequest(ConsultaRequest request){
        consultaRequestRepository.save(request);
    }
}
