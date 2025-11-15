package co.edu.javeriana.as.personapp.application.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.StudyInputPort;
import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Study;

@UseCase
public class StudyUseCase implements StudyInputPort {

    private final StudyOutputPort persistence;
    private final PersonOutputPort personOutput;
    private final ProfessionOutputPort professionOutput;

    public StudyUseCase(@Qualifier("studyOutputAdapterMaria") StudyOutputPort persistence,
            @Qualifier("personOutputAdapterMaria") PersonOutputPort personOutput,
            @Qualifier("professionOutputAdapterMaria") ProfessionOutputPort professionOutput) {
        this.persistence = persistence;
        this.personOutput = personOutput;
        this.professionOutput = professionOutput;
    }

    @Override
    public Study create(Study study) {
        if (study.getPerson() != null) {
            personOutput.save(study.getPerson());
        }
        if (study.getProfession() != null) {
            professionOutput.save(study.getProfession());
        }
        return persistence.save(study);
    }

    @Override
    public Study edit(Integer personId, Integer profId, Study study) throws NoExistException {
        Study old = persistence.findById(personId, profId);

        // If the study doesn't exist, treat PUT as upsert: create nested person/profession and save
        if (old == null) {
            if (study.getPerson() != null) {
                study.getPerson().setIdentification(personId);
                personOutput.save(study.getPerson());
            }
            if (study.getProfession() != null) {
                study.getProfession().setIdentification(profId);
                professionOutput.save(study.getProfession());
            }
            return persistence.save(study);
        }

        // ensure path ids are applied to the nested objects for an existing study
        if (study.getPerson() != null) {
            study.getPerson().setIdentification(personId);
            personOutput.save(study.getPerson());
        }
        if (study.getProfession() != null) {
            study.getProfession().setIdentification(profId);
            professionOutput.save(study.getProfession());
        }

        return persistence.save(study);
    }

    @Override
    public boolean delete(Integer personId, Integer profId) throws NoExistException {
        Study old = persistence.findById(personId, profId);

        if (old == null) {
            throw new NoExistException("No existe el estudio para la persona/profesion: " + personId + " / " + profId);
        }

        return persistence.delete(personId, profId);
    }

    @Override
    public Study findOne(Integer personId, Integer profId) throws NoExistException {
        Study s = persistence.findById(personId, profId);

        if (s == null) {
            throw new NoExistException("No existe el estudio para la persona/profesion: " + personId + " / " + profId);
        }

        return s;
    }

    @Override
    public List<Study> findAll() {
        return persistence.find();
    }
}
