package co.edu.javeriana.as.personapp.terminal.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.StudyInputPort;
import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.StudyUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class EstudioInputAdapterCli {

    @Autowired
    @Qualifier("studyOutputAdapterMaria")
    private StudyOutputPort studyOutputAdapterMaria;

    @Autowired
    @Qualifier("studyOutputAdapterMongo")
    private StudyOutputPort studyOutputAdapterMongo;

    @Autowired
    @Qualifier("personOutputAdapterMaria")
    private PersonOutputPort personOutputPortMaria;

    @Autowired
    @Qualifier("personOutputAdapterMongo")
    private PersonOutputPort personOutputPortMongo;

    @Autowired
    @Qualifier("professionOutputAdapterMaria")
    private ProfessionOutputPort professionOutputPortMaria;

    @Autowired
    @Qualifier("professionOutputAdapterMongo")
    private ProfessionOutputPort professionOutputPortMongo;

    private StudyInputPort studyInputPort;
    private PersonOutputPort currentPersonOutput;
    private ProfessionOutputPort currentProfessionOutput;

    public void setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
            studyInputPort = new StudyUseCase(studyOutputAdapterMaria, personOutputPortMaria, professionOutputPortMaria);
            currentPersonOutput = personOutputPortMaria;
            currentProfessionOutput = professionOutputPortMaria;
        } else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
            studyInputPort = new StudyUseCase(studyOutputAdapterMongo, personOutputPortMongo, professionOutputPortMongo);
            currentPersonOutput = personOutputPortMongo;
            currentProfessionOutput = professionOutputPortMongo;
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    // Return studies enriched with full Person and Profession when available
    public List<Study> historial() {
        log.info("Into historial StudyEntity in CLI Input Adapter");
        List<Study> raw = studyInputPort.findAll();
        return raw.stream().map(this::enrichStudyWithFullRefs).collect(Collectors.toList());
    }

    public Study crear(Study data) {
        log.info("Into crear StudyEntity in CLI Input Adapter");
        // If person exists in DB and data.person has only id, you can decide to skip updating,
        // but here we pass what user provided. Use 'buscarPersona' before to reuse existing.
        Study created = studyInputPort.create(data);
        return enrichStudyWithFullRefs(created);
    }

    public Study buscar(Integer personId, Integer profId) {
        log.info("Into buscar StudyEntity in CLI Input Adapter");
        try {
            Study s = studyInputPort.findOne(personId, profId);
            return s == null ? null : enrichStudyWithFullRefs(s);
        } catch (Exception e) {
            log.info("Study not found: {} / {}", personId, profId);
            return null;
        }
    }

    public Study editar(Integer personId, Integer profId, Study data) {
        log.info("Into editar StudyEntity in CLI Input Adapter");
        try {
            Study edited = studyInputPort.edit(personId, profId, data);
            return edited == null ? null : enrichStudyWithFullRefs(edited);
        } catch (Exception e) {
            log.info("Error editing study: {} / {}", personId, profId);
            return null;
        }
    }

    public boolean eliminar(Integer personId, Integer profId) {
        log.info("Into eliminar StudyEntity in CLI Input Adapter");
        try {
            return studyInputPort.delete(personId, profId);
        } catch (Exception e) {
            log.info("Error deleting study: {} / {}", personId, profId);
            return false;
        }
    }

    // Nuevo: obtener persona por id usando adaptador actual
    public Person buscarPersonaPorId(Integer id) {
        if (currentPersonOutput == null) return null;
        return currentPersonOutput.findById(id);
    }

    // Nuevo: obtener profesion por id usando adaptador actual
    public Profession buscarProfesionPorId(Integer id) {
        if (currentProfessionOutput == null) return null;
        return currentProfessionOutput.findById(id);
    }

    // Helper: si hay referencias solo con id, traigo el objeto completo y lo seteo.
    private Study enrichStudyWithFullRefs(Study s) {
        if (s == null) return null;
        try {
            if (s.getPerson() != null && s.getPerson().getIdentification() != null && currentPersonOutput != null) {
                Person full = currentPersonOutput.findById(s.getPerson().getIdentification());
                if (full != null) s.setPerson(full);
            }
            if (s.getProfession() != null && s.getProfession().getIdentification() != null
                    && currentProfessionOutput != null) {
                Profession fullProf = currentProfessionOutput.findById(s.getProfession().getIdentification());
                if (fullProf != null) s.setProfession(fullProf);
            }
        } catch (Exception e) {
            log.warn("No se pudo enriquecer study con referencias completas: {}", e.getMessage());
        }
        return s;
    }
}