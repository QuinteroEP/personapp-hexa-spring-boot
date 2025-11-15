package co.edu.javeriana.as.personapp.mariadb.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntityPK;
import co.edu.javeriana.as.personapp.mariadb.mapper.EstudiosMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.EstudiosRepositoryMaria;

@Repository("studyOutputAdapterMaria")
public class StudyOutputAdapterMaria implements StudyOutputPort {

    @Autowired
    private EstudiosRepositoryMaria repository;

    @Autowired
    private EstudiosMapperMaria mapper;

    @Override
    @Transactional
    public Study save(Study study) {
        EstudiosEntity entity = mapper.fromDomainToAdapter(study);
        EstudiosEntity saved = repository.save(entity);
        return mapper.fromAdapterToDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Study findById(Integer personId, Integer profId) {
        EstudiosEntityPK pk = new EstudiosEntityPK(profId, personId);
        return repository.findById(pk).map(mapper::fromAdapterToDomain).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Study> find() {
        return repository.findAll().stream().map(mapper::fromAdapterToDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean delete(Integer personId, Integer profId) {
        EstudiosEntityPK pk = new EstudiosEntityPK(profId, personId);
        repository.deleteById(pk);
        return true;
    }
}
