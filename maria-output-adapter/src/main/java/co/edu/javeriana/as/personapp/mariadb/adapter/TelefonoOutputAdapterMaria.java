package co.edu.javeriana.as.personapp.mariadb.adapter;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.application.port.out.TelefonoOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mariadb.entity.TelefonoEntity;
import co.edu.javeriana.as.personapp.mariadb.mapper.TelefonoMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.TelefonoRepositoryMaria;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter("telefonoOutputAdapterMaria")
@Transactional
public class TelefonoOutputAdapterMaria implements TelefonoOutputPort{
    @Autowired
	private TelefonoRepositoryMaria telefonoRepositoryMaria;

	@Autowired
	private TelefonoMapperMaria telefonoMapperMaria;

	@Override
	public Phone create(Phone telefono) {
		log.debug("Into save on Adapter MariaDB");
		TelefonoEntity persistedTelefono = telefonoRepositoryMaria.save(telefonoMapperMaria.fromDomainToAdapter(telefono));
		return telefonoMapperMaria.fromAdapterToDomain(persistedTelefono);
	}

	@Override
	public boolean delete(Phone telefono) {
		log.debug("Into delete on Adapter MariaDB");
        TelefonoEntity persistedTelefono = telefonoRepositoryMaria.save(telefonoMapperMaria.fromDomainToAdapter(telefono));
		telefonoRepositoryMaria.delete(persistedTelefono);
		return telefonoRepositoryMaria.findById(persistedTelefono.getNum()).isEmpty();
	}

	@Override
	public List<Phone> findAll() {
		log.debug("Into find on Adapter MariaDB");
		return telefonoRepositoryMaria.findAll().stream().map(telefonoMapperMaria::fromAdapterToDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Phone findByNumber(String identification) {
		log.debug("Into findById on Adapter MariaDB");
		if (telefonoRepositoryMaria.findById(identification).isEmpty()) {
			return null;
		} else {
			return telefonoMapperMaria.fromAdapterToDomain(telefonoRepositoryMaria.findById(identification).get());
		}
	}
}
