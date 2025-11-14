package co.edu.javeriana.as.personapp.application.port.out;

import co.edu.javeriana.as.personapp.domain.Profession;
import java.util.List;

public interface ProfessionOutputPort {

    Profession save(Profession profession);

    Profession findById(Integer id);

    List<Profession> find();

    boolean delete(Integer id);
}
