package nforla.tap.microservicioconsulta.repositorios;

import nforla.tap.microservicioconsulta.modelo.Persona;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonaRepository extends CrudRepository<Persona, ObjectId> {

    Optional<Persona> findByCuil(String cuil);

}
