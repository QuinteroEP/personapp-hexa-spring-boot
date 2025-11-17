package co.edu.javeriana.as.personapp.application.port.out;

import java.util.List;

import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.common.annotations.Port;

@Port
public interface TelefonoOutputPort {
    Phone create(Phone phone);

    boolean delete(Phone Phone) throws NoExistException;

    Phone findByNumber(String number) throws NoExistException;

    List<Phone> findAll();
}
