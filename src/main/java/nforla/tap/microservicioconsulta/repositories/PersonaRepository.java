package nforla.tap.microservicioconsulta.repositories;

import nforla.tap.microservicioconsulta.modelo.Persona;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface PersonaRepository extends CrudRepository<Persona, ObjectId> {
}
