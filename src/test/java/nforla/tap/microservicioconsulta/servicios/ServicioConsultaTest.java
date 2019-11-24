package nforla.tap.microservicioconsulta.servicios;

import nforla.tap.microservicioconsulta.excepciones.CuilNoValidoException;
import nforla.tap.microservicioconsulta.modelo.ConsultaResponse;
import nforla.tap.microservicioconsulta.modelo.Deuda;
import nforla.tap.microservicioconsulta.modelo.Persona;
import nforla.tap.microservicioconsulta.repositorios.PersonaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicioConsultaTest {

    @Mock
    ClienteRiesgo clienteRiesgo;
    @Mock
    PersonaRepository personaRepository;
    IServicioConsulta servicioConsulta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        servicioConsulta = new ServicioConsulta(personaRepository, clienteRiesgo);
    }

    @Test
    void givenCuilNoValidoThenThrowException() throws Exception {

        String cuilNoValido = "203976063a";

        assertThrows(CuilNoValidoException.class, () -> servicioConsulta.analizarEstadoPersona(cuilNoValido));

    }

    @Test
    void givenCuilNoExistenteThenReturnMensaje() throws Exception {

        String cuilValidoInexistente = "11111111111";
        Optional<Persona> personaOptional = Optional.empty();
        when(personaRepository.findByCuil(cuilValidoInexistente)).thenReturn(personaOptional);

        assertDoesNotThrow(() -> servicioConsulta.analizarEstadoPersona(cuilValidoInexistente));
        assertEquals("No se encontraron datos del solicitante para determinar el estado", servicioConsulta.analizarEstadoPersona(cuilValidoInexistente).getMensaje());

    }

    @Test
    void givenCuilValidoExistenteThenReturnConsultaResponse() throws Exception {

        Persona persona = new Persona("20212112223", Arrays.asList(new Deuda(20000, 4), new Deuda(20000, 2)));
        Optional<Persona> personaOptional = Optional.of(persona);
        when(personaRepository.findByCuil(persona.getCuil())).thenReturn(personaOptional);

        ConsultaResponse response = new ConsultaResponse(persona.getCuil(), 3);
        when(clienteRiesgo.determinarEstadoPersona(persona)).thenReturn(response);

        assertEquals(response, servicioConsulta.analizarEstadoPersona(persona.getCuil()));
    }


    @Test
    void givenArrayCuilsValidosThenReturnArrayConsultaResponse() throws Exception {

        List<String> cuils = Arrays.asList("20212222223", "20212222222");

        Persona persona = new Persona("20212222223", Arrays.asList(new Deuda(20000, 4), new Deuda(20000, 2)));
        Optional<Persona> personaOptional = Optional.of(persona);
        when(personaRepository.findByCuil(persona.getCuil())).thenReturn(personaOptional);
        Persona persona2 = new Persona("20212222222", Arrays.asList(new Deuda(20000, 5), new Deuda(20000, 1)));
        Optional<Persona> personaOptional2 = Optional.of(persona2);
        when(personaRepository.findByCuil(persona2.getCuil())).thenReturn(personaOptional2);

        ConsultaResponse response = new ConsultaResponse(persona.getCuil(), 3);
        ConsultaResponse response1 = new ConsultaResponse(persona2.getCuil(), 3);
        List<ConsultaResponse> responses = Arrays.asList(response, response1);

        when(clienteRiesgo.determinarEstadoPeronas(Arrays.asList(persona, persona2))).thenReturn(responses);

        List<ConsultaResponse> consultaResponses = servicioConsulta.analizarEstadoPersonas(cuils);

        assertThat(consultaResponses, hasSize(2));
        assertThat(consultaResponses, hasItems(response1, response));

    }

    @Test
    void givenArrayCuilsNoValidosThenReturnRespuestasSinEstado() throws Exception {

        List<String> cuils = Arrays.asList("20212222223", "2021222222a", "2021222222c");

        Optional<Persona> personaOptional = Optional.empty();
        when(personaRepository.findByCuil(cuils.get(0))).thenReturn(personaOptional);

        List<ConsultaResponse> consultaResponses = servicioConsulta.analizarEstadoPersonas(cuils);

        assertThat(consultaResponses, hasSize(3));
        for (ConsultaResponse response : consultaResponses) {
            assertEquals(0, response.getEstado());
            assertNotNull(response.getMensaje());
        }
    }

    @Test
    void givenArrayCuilsValidosYNoValidosThenReturnArrayConsultaResponse() throws Exception{

        List<String> cuils = Arrays.asList("20212222223", "2021222222a");

        Persona persona = new Persona("20212222223", Arrays.asList(new Deuda(20000, 4), new Deuda(20000, 2)));
        Optional<Persona> personaOptional = Optional.of(persona);
        when(personaRepository.findByCuil(persona.getCuil())).thenReturn(personaOptional);

        ConsultaResponse response = new ConsultaResponse(persona.getCuil(), 3);

        when(clienteRiesgo.determinarEstadoPeronas(Arrays.asList(persona))).thenReturn(new ArrayList<>(Arrays.asList(response)));

        List<ConsultaResponse> consultaResponses = servicioConsulta.analizarEstadoPersonas(cuils);

        assertNotEquals(0, consultaResponses.get(0).getEstado());
        assertNotNull(consultaResponses.get(1).getMensaje());
    }
}