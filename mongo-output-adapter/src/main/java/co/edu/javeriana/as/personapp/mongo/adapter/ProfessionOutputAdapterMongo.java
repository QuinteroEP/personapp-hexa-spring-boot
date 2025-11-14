package co.edu.javeriana.as.personapp.mongo.adapter;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument;
import co.edu.javeriana.as.personapp.mongo.mapper.ProfesionMapperMongo;
import co.edu.javeriana.as.personapp.mongo.repository.ProfesionRepositoryMongo;

@Repository("professionOutputAdapterMongo")
public class ProfessionOutputAdapterMongo implements ProfessionOutputPort {

    @Autowired
    private ProfesionRepositoryMongo repository;

    @Autowired
    private ProfesionMapperMongo mapper;

    @Override
    public Profession save(Profession profession) {
        ProfesionDocument doc = mapper.fromDomainToAdapter(profession);
        ProfesionDocument saved = repository.save(doc);
        return mapper.fromAdapterToDomain(saved);
    }

    @Override
    public Profession findById(Integer id) {
        return repository.findById(id)
                .map(mapper::fromAdapterToDomain)
                .orElse(null);
    }

    @Override
    public List<Profession> find() {
        return repository.findAll().stream()
                .map(mapper::fromAdapterToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Integer id) {
        repository.deleteById(id);
        return true;
    }
}
