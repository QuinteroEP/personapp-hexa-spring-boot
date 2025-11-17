package co.edu.javeriana.as.personapp.application.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.TelefonoInputPort;
import co.edu.javeriana.as.personapp.application.port.out.TelefonoOutputPort;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;

@UseCase
public class TelefonoUseCase implements TelefonoInputPort{
    private final TelefonoOutputPort persistence;

    public TelefonoUseCase(
            @Qualifier("telefonoOutputAdapterMaria")
            TelefonoOutputPort persistence) {
        this.persistence = persistence;
    }

    @Override
    public Phone create(Phone telefono) {
        return persistence.create(telefono);
    }

    @Override
    public Phone edit(Phone telefono) throws NoExistException {
        Phone old = persistence.findByNumber(telefono.getNumber());

        if (old != null){
            telefono.setOwner(old.getOwner());
			return persistence.create(telefono);
		}
		throw new NoExistException(
				"The phone with number " + telefono.getNumber() + " does not exist into db, number cannot be edited");
    }

    @Override
    public boolean delete(Phone phone) throws NoExistException {
        Phone old = persistence.findByNumber(phone.getNumber());

        if (old == null) {
            throw new NoExistException("No existe el telefono " + phone.getNumber());
        }

        return persistence.delete(old);
    }

    @Override
    public Phone findByNumber(String numnber) throws NoExistException {
        Phone p = persistence.findByNumber(numnber);

        if (p == null) {
            throw new NoExistException("No existe el telefono " + numnber);
        }

        return p;
    }

    @Override
    public List<Phone> findAll() {
        return persistence.findAll();
    }

    @Override
    public Integer count() {
        return findAll().size();
    }

    @Override
    public Phone findOne(Integer ownerId) throws NoExistException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }
}
