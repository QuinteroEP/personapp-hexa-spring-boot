package co.edu.javeriana.as.personapp.mariadb.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.mariadb.entity.ProfesionEntity;
import co.edu.javeriana.as.personapp.mariadb.mapper.ProfesionMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.ProfesionRepositoryMaria;

@Repository("professionOutputAdapterMaria")
public class ProfessionOutputAdapterMaria implements ProfessionOutputPort {

    @Autowired
    private ProfesionRepositoryMaria repository;

    @Autowired
    private ProfesionMapperMaria mapper;

    @Override
    @Transactional
    public Profession save(Profession profession) {
        ProfesionEntity entity = mapper.fromDomainToAdapter(profession);
        ProfesionEntity saved = repository.save(entity);
        return mapper.fromAdapterToDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Profession findById(Integer id) {
        return repository.findById(id)
                .map(mapper::fromAdapterToDomain)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
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
