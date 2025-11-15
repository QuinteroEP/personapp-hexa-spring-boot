package co.edu.javeriana.as.personapp.mongo.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument;
import co.edu.javeriana.as.personapp.mongo.mapper.EstudiosMapperMongo;
import co.edu.javeriana.as.personapp.mongo.repository.EstudiosRepositoryMongo;

@Repository("studyOutputAdapterMongo")
public class StudyOutputAdapterMongo implements StudyOutputPort {

    @Autowired
    private EstudiosRepositoryMongo repository;

    @Autowired
    private EstudiosMapperMongo mapper;

    @Override
    public Study save(Study study) {
        EstudiosDocument doc = mapper.fromDomainToAdapter(study);
        EstudiosDocument saved = repository.save(doc);
        return mapper.fromAdapterToDomain(saved);
    }

    @Override
    public Study findById(Integer personId, Integer profId) {
        String id = personId + "-" + profId;
        return repository.findById(id).map(mapper::fromAdapterToDomain).orElse(null);
    }

    @Override
    public List<Study> find() {
        return repository.findAll().stream().map(mapper::fromAdapterToDomain).collect(Collectors.toList());
    }

    @Override
    public boolean delete(Integer personId, Integer profId) {
        String id = personId + "-" + profId;
        repository.deleteById(id);
        return true;
    }
}
