package co.edu.javeriana.as.personapp.application.port.in;

import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Study;
import java.util.List;

public interface StudyInputPort {

    Study create(Study study);

    Study edit(Integer personId, Integer profId, Study study) throws NoExistException;

    boolean delete(Integer personId, Integer profId) throws NoExistException;

    Study findOne(Integer personId, Integer profId) throws NoExistException;

    List<Study> findAll();
}
