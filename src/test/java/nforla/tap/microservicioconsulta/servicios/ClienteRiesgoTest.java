package nforla.tap.microservicioconsulta.servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import nforla.tap.microservicioconsulta.excepciones.DeterminarEstadoException;
import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;
import nforla.tap.microservicioconsulta.modelo.Persona;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class ClienteRiesgoTest {

    private OkHttpClient httpClient;
    private ClienteRiesgo clienteRiesgo;
    private ObjectMapper objectMapper;
    private Request request;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();
        request = new Request.Builder()
                .url("http://localhost:8888")
                .build();

    }

    @Test
    void givenPersonaThenReturnConsultaResponse() throws Exception {

        Interceptor interceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                ResponseBody responseBody = ResponseBody.create("{\"cuil\": \"20357666993\",\"estado\": 3}", MediaType.parse("application/json"));

                return new Response.Builder()
                        .code(200)
                        .body(responseBody)
                        .request(request)
                        .protocol(Protocol.HTTP_2)
                        .message("")
                        .build();
            }
        };

        httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        clienteRiesgo = new ClienteRiesgo(httpClient, objectMapper);
        ReflectionTestUtils.setField(clienteRiesgo, "ANALIZAR_ESTADO_PERSONA_URL", "http://localhost:8888");

        Persona persona = new Persona("20357666993");

        ConsultaResponse consultaResponse = clienteRiesgo.determinarEstadoPersona(persona);

        assertNotNull(consultaResponse);
        assertNotEquals(0, consultaResponse.getEstado());

    }

    @Test
    void given400ReponseCodeThenThrowException() throws Exception{

        Interceptor interceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                ResponseBody responseBody = ResponseBody.create("", MediaType.parse("application/json"));

                return new Response.Builder()
                        .code(400)
                        .body(responseBody)
                        .request(request)
                        .protocol(Protocol.HTTP_2)
                        .message("")
                        .build();
            }
        };

        httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        clienteRiesgo = new ClienteRiesgo(httpClient, objectMapper);
        ReflectionTestUtils.setField(clienteRiesgo, "ANALIZAR_ESTADO_PERSONA_URL", "http://localhost:8888");

        Persona persona = new Persona("20357666993");

        assertThrows(DeterminarEstadoException.class, () -> clienteRiesgo.determinarEstadoPersona(persona));
    }

    @Test
    void givenArrayPersonasThenReturnResponseArray() throws Exception{

        String jsonResponse = "[{\"cuil\":\"20357666993\",\"estado\":3},{\"cuil\":\"20357666996\",\"estado\":4}]";
        Interceptor interceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                ResponseBody responseBody = ResponseBody.create(jsonResponse, MediaType.parse("application/json"));

                return new Response.Builder()
                        .code(200)
                        .body(responseBody)
                        .request(request)
                        .protocol(Protocol.HTTP_2)
                        .message("")
                        .build();
            }
        };

        httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        clienteRiesgo = new ClienteRiesgo(httpClient, objectMapper);
        ReflectionTestUtils.setField(clienteRiesgo, "ANALIZAR_ESTADO_PERSONAS_URL", "http://localhost:8888");

        List<Persona> personas = Arrays.asList(new Persona("20357666993"), new Persona("20357666996"));

        List<ConsultaResponse> responses = clienteRiesgo.determinarEstadoPeronas(personas);

        assertNotNull(responses);
        assertThat(responses, hasSize(personas.size()));
    }
}