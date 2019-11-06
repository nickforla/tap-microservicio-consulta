package nforla.tap.microservicioconsulta.servicios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;
import nforla.tap.microservicioconsulta.modelo.Persona;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ClienteRiesgo implements IClienteRiesgo {

    private final Logger logger = LoggerFactory.getLogger(ClienteRiesgo.class);
    private OkHttpClient httpClient;
    private ObjectMapper objectMapper;

    public ClienteRiesgo(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public ConsultaResponse determinarEstadoPersona(Persona persona) throws IOException {
        logger.info("CONSULTADO AL MS DE RISGO");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), objectMapper.writeValueAsString(persona));

        Request request = new Request.Builder()
                .url("http://localhost:8085/analizarEstado/persona")
                .post(body)
                .build();

        Call call = httpClient.newCall(request);

        Response response = call.execute();

        return objectMapper.readValue(response.body().string(), ConsultaResponse.class);

    }

    @Override
    public List<ConsultaResponse> determinarEstadoPeronas(List<Persona> personas) {
        return null;
    }
}
