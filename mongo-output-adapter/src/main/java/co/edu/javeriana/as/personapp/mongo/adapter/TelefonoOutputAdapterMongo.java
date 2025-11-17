package co.edu.javeriana.as.personapp.mongo.adapter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.application.port.out.TelefonoOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mongo.mapper.TelefonoMapperMongo;
import co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument;
import co.edu.javeriana.as.personapp.mongo.repository.TelefonoRepositoryMongo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter("telefonoOutputAdapterMongo")
public class TelefonoOutputAdapterMongo implements TelefonoOutputPort{
     @Autowired
	private TelefonoRepositoryMongo telefonoRepositoryMongo;

	@Autowired
	private TelefonoMapperMongo telefonoMapperMongo;

	@Override
	public Phone create(Phone telefono) {
		log.debug("Into save on Adapter Mongo");
		TelefonoDocument persistedTelefono = telefonoRepositoryMongo.save(telefonoMapperMongo.fromDomainToAdapter(telefono));
		return telefonoMapperMongo.fromAdapterToDomain(persistedTelefono);
	}

	@Override
	public boolean delete(Phone telefono) {
		log.debug("Into delete on Adapter Mongo");
        TelefonoDocument persistedTelefono = telefonoRepositoryMongo.save(telefonoMapperMongo.fromDomainToAdapter(telefono));
		telefonoRepositoryMongo.delete(persistedTelefono);
		return telefonoRepositoryMongo.findById(persistedTelefono.getId()).isEmpty();
	}

	@Override
	public List<Phone> findAll() {
		log.debug("Into find on Adapter Mongo");
		return telefonoRepositoryMongo.findAll().stream().map(telefonoMapperMongo::fromAdapterToDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Phone findByNumber(String identification) {
		log.debug("Into findById on Adapter Mongo");
		if (telefonoRepositoryMongo.findById(identification).isEmpty()) {
			return null;
		} else {
			return telefonoMapperMongo.fromAdapterToDomain(telefonoRepositoryMongo.findById(identification).get());
		}
	}
}
