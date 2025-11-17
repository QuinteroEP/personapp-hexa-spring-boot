package co.edu.javeriana.as.personapp.terminal.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.terminal.mapper.PersonaMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;
import lombok.extern.slf4j.Slf4j;
import co.edu.javeriana.as.personapp.domain.Person;

@Slf4j
@Adapter
public class PersonaInputAdapterCli {

	@Autowired
	@Qualifier("personOutputAdapterMaria")
	private PersonOutputPort personOutputPortMaria;

	@Autowired
	@Qualifier("personOutputAdapterMongo")
	private PersonOutputPort personOutputPortMongo;

	@Autowired
	private PersonaMapperCli personaMapperCli;

	PersonInputPort personInputPort;

	public void setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMaria);
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMongo);
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public void historial1() {
		log.info("Into historial PersonaEntity in Input Adapter");
		List<PersonaModelCli> persona = personInputPort.findAll().stream().map(personaMapperCli::fromDomainToAdapterCli)
					.collect(Collectors.toList());
		persona.forEach(p -> System.out.println(p.toString()));
	}
	public void historial() {
	    log.info("Into historial PersonaEntity in Input Adapter");
	    personInputPort.findAll().stream()
	        .map(personaMapperCli::fromDomainToAdapterCli)
	        .forEach(System.out::println);
		System.out.println("Total: " + personInputPort.count());
	}

	public Person crear(Person data) {
		log.info("Into crear PersonaEntity in Input Adapter");
		Person nueva = personInputPort.create(data);
		return nueva;
	}

	public Person buscar(Integer id) {
		log.info("Into buscar PersonaEntity in Input Adapter");
		Person persona;
		try {
			persona = personInputPort.findOne(id);
			System.out.println(persona);
			return persona;
		} catch (NoExistException e) {
			log.info("Error: Persona no encontrada");
			System.out.println("Error: La persona con id " + id + " no existe");
			e.printStackTrace();
			return null;
		}
	}

	public Person editar(Integer id, Person data) {
		log.info("Into editar PersonaEntity in Input Adapter");
		Person editado;
		try {
			editado = personInputPort.edit(id, data);
		} catch (NoExistException e) {
			System.out.println("Error: la persona no existe");
			editado = null;
			e.printStackTrace();
		}
		return editado;
	}

	public void eliminar(Integer id) {
		log.info("Into elimiar PersonaEntity in Input Adapter");
		try {
			personInputPort.drop(id);
		} catch (NoExistException e) {
			log.info("Error: Persona no encontrada");
			e.printStackTrace();
		}
	}

}
