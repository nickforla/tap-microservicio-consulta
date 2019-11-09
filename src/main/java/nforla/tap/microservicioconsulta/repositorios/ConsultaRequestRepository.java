package nforla.tap.microservicioconsulta.repositorios;

import nforla.tap.microservicioconsulta.modelo.ConsultaRequest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public interface ConsultaRequestRepository extends MongoRepository<ConsultaRequest, ObjectId> {

    Stream<ConsultaRequest> streamByUsernameAndHoraRequestBetween(String username, LocalDateTime desde, LocalDateTime hasta);

}
