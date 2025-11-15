package co.edu.javeriana.as.personapp.application.port.out;

import co.edu.javeriana.as.personapp.domain.Study;
import java.util.List;

public interface StudyOutputPort {

    Study save(Study study);

    Study findById(Integer personId, Integer profId);

    List<Study> find();

    boolean delete(Integer personId, Integer profId);
}
