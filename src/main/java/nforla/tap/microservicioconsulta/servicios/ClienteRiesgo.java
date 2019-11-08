package nforla.tap.microservicioconsulta.servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import nforla.tap.microservicioconsulta.excepciones.DeterminarEstadoException;
import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;
import nforla.tap.microservicioconsulta.modelo.Persona;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

@Component
public class ClienteRiesgo implements IClienteRiesgo {

    private final Logger logger = LoggerFactory.getLogger(ClienteRiesgo.class);
    private OkHttpClient httpClient;
    private ObjectMapper objectMapper;
    private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    @Value("${riesgo.analizar-estado-persona-url}")
    private String ANALIZAR_ESTADO_PERSONA_URL;
    @Value("${riesgo.analizar-estado-personas-url}")
    private String ANALIZAR_ESTADO_PERSONAS_URL;

    public ClienteRiesgo(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public ConsultaResponse determinarEstadoPersona(Persona persona) throws IOException, DeterminarEstadoException {

        logger.info("Solicitando estado al microservicio de riesgo --> " + persona);

        Response response = sendRequest(objectMapper.writeValueAsString(persona), ANALIZAR_ESTADO_PERSONA_URL);

        if(response.code() == 200){
            return objectMapper.readValue(response.body().string(), ConsultaResponse.class);
        }

        throw new DeterminarEstadoException("La respuesta del servicio de riesgo no fue exitosa..");
    }

    private Response sendRequest(String json, String url) throws IOException{

        RequestBody body = RequestBody.create(json, MEDIA_TYPE);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = httpClient.newCall(request);

        return call.execute();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ConsultaResponse> determinarEstadoPeronas(List<Persona> personas) throws IOException, DeterminarEstadoException{

        logger.info("Solicitando estados al microservicio de riesgo --> " + personas);

        Response response = sendRequest(objectMapper.writeValueAsString(personas), ANALIZAR_ESTADO_PERSONAS_URL);

        if(response.code() == 200){
            return (List<ConsultaResponse>) objectMapper.readValue(response.body().string(), List.class);
        }

        throw new DeterminarEstadoException("La respuesta del servicio de riesgo no fue exitosa..");
    }
}
