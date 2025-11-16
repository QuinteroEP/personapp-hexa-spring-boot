package co.edu.javeriana.as.personapp.terminal.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.ProfessionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.ProfessionUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.terminal.mapper.profesionMapperCli;

import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;
import lombok.extern.slf4j.Slf4j;

import co.edu.javeriana.as.personapp.domain.Profession;

@Slf4j
@Adapter
public class ProfesionInputAdapterCli {

	@Autowired
	@Qualifier("professionOutputAdapterMaria")
	private ProfessionOutputPort ProfessionOutputPortMaria;

	@Autowired
	@Qualifier("professionOutputAdapterMongo")
	private ProfessionOutputPort ProfessionOutputPortMongo;

	@Autowired
	private profesionMapperCli profesionMapperCli;

	ProfessionInputPort professionInputPort;

	public void setProfessionOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			professionInputPort = new ProfessionUseCase(ProfessionOutputPortMaria);
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			professionInputPort = new ProfessionUseCase(ProfessionOutputPortMongo);
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public void historial1() {
		log.info("Into historial ProfesionEntity in Input Adapter");
		List<ProfesionModelCli> profesion = professionInputPort.findAll().stream().map(profesionMapperCli::fromDomainToAdapterCli)
					.collect(Collectors.toList());
		profesion.forEach(p -> System.out.println(p.toString()));
	}
	public void historial() {
	    log.info("Into historial ProfesionEntity in Input Adapter");
	    professionInputPort.findAll().stream()
	        .map(profesionMapperCli::fromDomainToAdapterCli)
	        .forEach(System.out::println);
		System.out.println("Total: " + professionInputPort.count());
	}

	public Profession crear(Profession data) {
		log.info("Into crear ProfesionEntity in Input Adapter");
		Profession nueva = professionInputPort.create(data);
		return nueva;
	}

	public void buscar(Integer id) {
		log.info("Into buscar ProfesionEntity in Input Adapter");
		Profession profesion;
		try {
			profesion = professionInputPort.findOne(id);
			System.out.println(profesion);
		} catch (NoExistException e) {
			log.info("Error: profesion no encontrada");
			System.out.println("Error: La profesion con id " + id + " no existe");
			e.printStackTrace();
		}
	}

	public Profession editar(Integer id, Profession data) {
		log.info("Into editar ProfesionEntity in Input Adapter");
		Profession editado;
		try {
			editado = professionInputPort.edit(id, data);
		} catch (NoExistException e) {
			System.out.println("Error: la profesion no existe");
			editado = null;
			e.printStackTrace();
		}
		return editado;
	}

	public void eliminar(Integer id) {
		log.info("Into elimiar ProfesionEntity in Input Adapter");
		try {
			professionInputPort.drop(id);
		} catch (NoExistException e) {
			log.info("Error: Profesion no encontrada");
			e.printStackTrace();
		}
	}

}
