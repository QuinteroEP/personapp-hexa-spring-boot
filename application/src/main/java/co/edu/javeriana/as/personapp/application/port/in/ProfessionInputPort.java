package co.edu.javeriana.as.personapp.application.port.in;

import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Profession;
import java.util.List;

public interface ProfessionInputPort {

    Profession create(Profession profession);

    Profession edit(Integer id, Profession profession) throws NoExistException;

    boolean drop(Integer id) throws NoExistException;

    Profession findOne(Integer id) throws NoExistException;
    
    public Integer count();

    List<Profession> findAll();
}
