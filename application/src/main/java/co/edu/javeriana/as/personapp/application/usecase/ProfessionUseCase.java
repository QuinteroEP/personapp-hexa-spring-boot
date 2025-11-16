package co.edu.javeriana.as.personapp.application.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.ProfessionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;

@UseCase
public class ProfessionUseCase implements ProfessionInputPort {

    private final ProfessionOutputPort persistence;

    public ProfessionUseCase(
            @Qualifier("professionOutputAdapterMaria")
            ProfessionOutputPort persistence) {
        this.persistence = persistence;
    }

    @Override
    public Profession create(Profession profession) {
        return persistence.save(profession);
    }

    @Override
    public Profession edit(Integer id, Profession profession) throws NoExistException {
        Profession old = persistence.findById(id);

        if (old != null){
			profession.setIdentification(id);
			return persistence.save(profession);
		}
		throw new NoExistException(
				"The person with id " + id + " does not exist into db, cannot be edited");
    }

    @Override
    public boolean drop(Integer id) throws NoExistException {
        Profession old = persistence.findById(id);

        if (old == null) {
            throw new NoExistException("No existe la profesión con ID: " + id);
        }

        return persistence.delete(id);
    }

    @Override
    public Profession findOne(Integer id) throws NoExistException {
        Profession p = persistence.findById(id);

        if (p == null) {
            throw new NoExistException("No existe la profesión con ID: " + id);
        }

        return p;
    }

    @Override
    public List<Profession> findAll() {
        return persistence.find();
    }

    @Override
    public Integer count() {
        return findAll().size();
    }
}
