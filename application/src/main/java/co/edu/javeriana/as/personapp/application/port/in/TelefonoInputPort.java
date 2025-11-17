package co.edu.javeriana.as.personapp.application.port.in;

import java.util.List;

import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.common.annotations.Port;

@Port
public interface TelefonoInputPort {
    
    Phone create(Phone phone);

    Phone edit(Phone Phone) throws NoExistException;

    boolean delete(Phone Phone) throws NoExistException;

    Phone findOne(Integer ownerId) throws NoExistException;

    Phone findByNumber(String number) throws NoExistException;

    List<Phone> findAll();

    public Integer count();
}
