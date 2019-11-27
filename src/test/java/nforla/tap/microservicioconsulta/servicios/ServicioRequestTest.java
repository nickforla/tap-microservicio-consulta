package nforla.tap.microservicioconsulta.servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import nforla.tap.microservicioconsulta.TimeStampProvider;
import nforla.tap.microservicioconsulta.excepciones.CuotaMaximaRequestsSuperadaException;
import nforla.tap.microservicioconsulta.modelo.ConsultaRequest;
import nforla.tap.microservicioconsulta.repositorios.ConsultaRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class})
class ServicioRequestTest {

    private IServicioRequest servicioRequest;
    private ObjectMapper objectMapper;
    @Mock
    ConsultaRequestRepository consultaRequestRepository;
    @Mock
    TimeStampProvider timeStampProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        objectMapper = new ObjectMapper();
        servicioRequest = new ServicioRequest(consultaRequestRepository, objectMapper, timeStampProvider);
    }

    @Test
    void givenUsuarioSuperaRequestsPermitidosThenThrowException() {

        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJyb2wiOlsiVVNFUiJdLCJjdGEiOjIwfQ.wYy2En2WtX-VO2C88Inf1qYiHw8gSY491JLsBHK1hkE";
        String username = "1234567890";

        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemDefaultZone());
        when(timeStampProvider.getTimeStamp()).thenReturn(localDateTime);

        ConsultaRequest consultaRequest = new ConsultaRequest(username, localDateTime, 18);
        Stream<ConsultaRequest> consultaRequestStream =  Stream.of(consultaRequest);

        when(consultaRequestRepository.streamByUsernameAndHoraRequestBetween(username, localDateTime.minusHours(1), localDateTime))
        .thenReturn(consultaRequestStream);

        assertThrows(CuotaMaximaRequestsSuperadaException.class, () -> servicioRequest.doCuotaRequestFilter(jwt, 5));

    }

    @Test
    void givenUsuarioNoSuperaRequestsPermitidosThenReturnConsultaRequest() throws Exception{

        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJyb2wiOlsiVVNFUiJdLCJjdGEiOjIwfQ.wYy2En2WtX-VO2C88Inf1qYiHw8gSY491JLsBHK1hkE";
        String username = "1234567890";

        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemDefaultZone());
        when(timeStampProvider.getTimeStamp()).thenReturn(localDateTime);

        ConsultaRequest consultaRequest = new ConsultaRequest(username, localDateTime, 18);
        Stream<ConsultaRequest> consultaRequestStream =  Stream.of(consultaRequest);

        when(consultaRequestRepository.streamByUsernameAndHoraRequestBetween(username, localDateTime.minusHours(1), localDateTime))
                .thenReturn(consultaRequestStream);

        assertEquals(consultaRequest.getClass(), servicioRequest.doCuotaRequestFilter(jwt, 2).getClass());
    }
}