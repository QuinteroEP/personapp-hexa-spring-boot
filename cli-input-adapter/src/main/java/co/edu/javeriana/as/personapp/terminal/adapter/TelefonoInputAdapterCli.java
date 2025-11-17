package co.edu.javeriana.as.personapp.terminal.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.TelefonoInputPort;
import co.edu.javeriana.as.personapp.application.port.out.TelefonoOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.TelefonoUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.terminal.mapper.TelefonoMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.TelefonoModelCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class TelefonoInputAdapterCli {
    @Autowired
	@Qualifier("telefonoOutputAdapterMaria")
	private TelefonoOutputPort telefonoOutputPortMaria;

	@Autowired
	@Qualifier("telefonoOutputAdapterMongo")
	private TelefonoOutputPort telefonoOutputPortMongo;

	@Autowired
	private TelefonoMapperCli telefonoMapperCli;

    @Autowired
	private PersonaInputAdapterCli personaInputAdapterCli;

	TelefonoInputPort telefonoInputPort;

    public void setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			telefonoInputPort = new TelefonoUseCase(telefonoOutputPortMaria);
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			telefonoInputPort = new TelefonoUseCase(telefonoOutputPortMongo);
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public void historial1() {
		log.info("Into historial TelefonoEntity in Input Adapter");
		List<TelefonoModelCli> telefono = telefonoInputPort.findAll().stream().map(telefonoMapperCli::fromDomainToAdapterCli)
					.collect(Collectors.toList());
		telefono.forEach(p -> System.out.println(p.toString()));
	}
	public void historial() {
	    log.info("Into historial TelefonoEntity in Input Adapter");
	    telefonoInputPort.findAll().stream()
	        .map(telefonoMapperCli::fromDomainToAdapterCli)
	        .forEach(System.out::println);
		System.out.println("Total: " + telefonoInputPort.count());
	}

	public Phone crear(Integer id, Phone data) {
		log.info("Into crear TelefonoEntity in Input Adapter");
        data.setOwner(personaInputAdapterCli.buscar(id));
		Phone nueva = telefonoInputPort.create(data);
		return nueva;
	}

	public void buscar(String id) {
		log.info("Into buscar TelefonoEntity in Input Adapter");
		Phone telefono;
		try {
			telefono = telefonoInputPort.findByNumber(id);
			System.out.println(telefono);
		} catch (NoExistException e) {
			log.info("Error: Telefono no encontrada");
			System.out.println("Error: La persona con id " + id + " no existe");
			e.printStackTrace();
		}
	}

	public Phone editar(Phone data) {
		log.info("Into editar TelefonoEntity in Input Adapter");
		Phone editado;
		try {
			editado = telefonoInputPort.edit(data);
		} catch (NoExistException e) {
			System.out.println("Error: la persona no existe");
			editado = null;
			e.printStackTrace();
		}
		return editado;
	}

	public void eliminar( Phone telefono) {
		log.info("Into elimiar TelefonoEntity in Input Adapter");
		try {
			telefonoInputPort.delete(telefono);
		} catch (NoExistException e) {
			log.info("Error: Persona no encontrada");
			e.printStackTrace();
		}
	}

    public Phone findByNumber(String number){
        try {
			return telefonoInputPort.findByNumber(number);
		} catch (NoExistException e) {
			log.info("Error: Persona no encontrada");
			e.printStackTrace();
            return null;
		}
    }
}
